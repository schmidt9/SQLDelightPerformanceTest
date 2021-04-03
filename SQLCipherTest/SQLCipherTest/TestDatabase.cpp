//
//  DatabseTest.cpp
//  SQLCipherTest
//
//  Created by Alexander Kormanovsky on 02.04.2021.
//

#include "TestDatabase.hpp"

namespace test {
    
    TestDatabase::TestDatabase(const std::string &databasePath)
    {
        auto result = sqlite3_open_v2(databasePath.c_str(), &mDb, SQLITE_OPEN_READWRITE | SQLITE_OPEN_CREATE , NULL);
        
        if (result != SQLITE_OK) {
            printError();
            return;
        }
        
        createTable();
    }
    
    /**
     * @return Compiled statement, call finalize() in caller after use
     */
    sqlite3_stmt*
    TestDatabase::prepare(const std::string &query)
    {
        sqlite3_stmt *stmt = NULL;
        
        if (sqlite3_prepare_v2(mDb, query.c_str(), -1, &stmt, NULL) != SQLITE_OK) {
            printError();
        }
        
        return stmt;
    }
    
    bool
    TestDatabase::exec(const std::string &query)
    {
        if (sqlite3_exec(mDb, query.c_str(), NULL, NULL, NULL) != SQLITE_OK) {
            printError();
            return false;
        }
        
        return true;
    }
    
    bool
    TestDatabase::step(sqlite3_stmt *stmt, int flag)
    {
        return sqlite3_step(stmt) == flag;
    }
    
    bool TestDatabase::stepRow(sqlite3_stmt *stmt)
    {
        return step(stmt, SQLITE_ROW);
    }
    
    bool TestDatabase::stepDone(sqlite3_stmt *stmt)
    {
        return step(stmt, SQLITE_DONE);
    }
    
    void
    TestDatabase::finalize(sqlite3_stmt *stmt)
    {
        sqlite3_finalize(stmt);
    }
    
    void
    TestDatabase::printError()
    {
        printf("SQLite error: %s code %d\n", sqlite3_errmsg(mDb), sqlite3_errcode(mDb));
    }
    
    void
    TestDatabase::createTable()
    {
        std::string query = "CREATE TABLE IF NOT EXISTS project (`_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`name` TEXT,`created` INTEGER DEFAULT (strftime('%s','now')),`update_time` INTEGER DEFAULT (strftime('%s','now')),`is_active` INTEGER DEFAULT 0)";
        exec(query);
    }
    
    void
    TestDatabase::clearProjectsTable()
    {
        std::string query = "DELETE FROM project";
        exec(query);
    }
    
    void
    TestDatabase::createProjects()
    {
        clearProjectsTable();
        
        // begin transaction
        
        std::string query = "BEGIN TRANSACTION";
        
        if (!exec(query)) {
            return;
        }
        
        // insert
        
        query = "INSERT INTO project (name) VALUES (?)";
        
        if (auto stmt = prepare(query)) {
            
            for (int i = 0; i < 100000; ++i) {
                auto projectName = "Project " + std::to_string(i);
                sqlite3_bind_text(stmt, 1, projectName.c_str(), (int) projectName.size(), SQLITE_STATIC);
                stepDone(stmt);
            }
            
            finalize(stmt);
        } else {
            return;
        }
        
        // commit
        
        query = "COMMIT";
        exec(query);
    }
    
    std::vector<Project>
    TestDatabase::fetchProjects()
    {
        std::vector<Project> result;
        std::string query = "SELECT * FROM project";
        
        if (auto stmt = prepare(query)) {
            while (stepRow(stmt)) {
                auto project = Project();
                project.id = sqlite3_column_int(stmt, 0);
                project.name = reinterpret_cast<const char*>(sqlite3_column_text(stmt, 1));
                project.created = sqlite3_column_int64(stmt, 2);
                project.updateTime = sqlite3_column_int64(stmt, 3);
                project.isActive = (sqlite3_column_int(stmt, 4) > 0);
                result.push_back(project);
            }
            
            finalize(stmt);
        }
        
        return result;
    }
    
}

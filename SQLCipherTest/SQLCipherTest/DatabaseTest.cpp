//
//  DatabseTest.cpp
//  SQLCipherTest
//
//  Created by Alexander Kormanovsky on 02.04.2021.
//

#include "DatabaseTest.hpp"

namespace test {
    
    DatabaseTest::DatabaseTest(const std::string &databasePath)
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
    DatabaseTest::prepare(const std::string &query)
    {
        sqlite3_stmt *stmt = NULL;
        
        if (sqlite3_prepare_v2(mDb, query.c_str(), -1, &stmt, NULL) != SQLITE_OK) {
            printError();
        }
        
        return stmt;
    }
    
    bool
    DatabaseTest::exec(const std::string &query)
    {
        if (sqlite3_exec(mDb, query.c_str(), NULL, NULL, NULL) != SQLITE_OK) {
            printError();
            return false;
        }
        
        return true;
    }
    
    bool
    DatabaseTest::step(sqlite3_stmt *stmt, int flag)
    {
        return sqlite3_step(stmt) == flag;
    }
    
    bool DatabaseTest::stepRow(sqlite3_stmt *stmt)
    {
        return step(stmt, SQLITE_ROW);
    }
    
    bool DatabaseTest::stepDone(sqlite3_stmt *stmt)
    {
        return step(stmt, SQLITE_DONE);
    }
    
    void
    DatabaseTest::finalize(sqlite3_stmt *stmt)
    {
        sqlite3_finalize(stmt);
    }
    
    void
    DatabaseTest::printError()
    {
        printf("SQLite error: %s code %d\n", sqlite3_errmsg(mDb), sqlite3_errcode(mDb));
    }
    
    void
    DatabaseTest::createTable()
    {
        std::string query = "CREATE TABLE IF NOT EXISTS project (`_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`name` TEXT,`created` INTEGER DEFAULT (strftime('%s','now')),`update_time` INTEGER DEFAULT (strftime('%s','now')),`is_active` INTEGER DEFAULT 0)";
        exec(query);
    }
    
    void
    DatabaseTest::createProjects()
    {
        // begin transaction
        
        std::string query = "BEGIN TRANSACTION";
        
        if (auto stmt = prepare(query)) {
            stepDone(stmt);
            finalize(stmt);
        } else {
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
        
        if (auto stmt = prepare(query)) {
            stepDone(stmt);
            finalize(stmt);
        }
    }
    
    std::vector<Project>
    DatabaseTest::fetchProjects()
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

//
//  DatabseTest.hpp
//  SQLCipherTest
//
//  Created by Alexander Kormanovsky on 02.04.2021.
//

#ifndef DatabaseTest_hpp
#define DatabaseTest_hpp

#include <stdio.h>
#include <string>
#include <vector>
#include <sqlite3.h>

namespace test {
    
    struct Project {
        
        int64_t id;
        std::string name;
        int64_t created;
        int64_t updateTime;
        bool isActive;
        
    };
    
    class TestDatabase {
        
    public:
        
        TestDatabase(const std::string &databasePath);
        
    private:
        
        sqlite3_stmt* prepare(const std::string &query);
        
        bool exec(const std::string &query);
        
        bool step(sqlite3_stmt *stmt, int flag);
        
        bool stepRow(sqlite3_stmt *stmt);
        
        bool stepDone(sqlite3_stmt *stmt);
        
        void finalize(sqlite3_stmt *stmt);
        
        void clearProjectsTable();
        
        void createTable();
        
    public:
        
        void createProjects(int count);
        
        std::vector<Project> fetchProjects();

        std::string getLastError() const;
        
    private:

        sqlite3 *mDb;
        
    };
    
}

#endif /* DatabaseTest_hpp */

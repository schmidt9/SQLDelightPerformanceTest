//
//  TDTestDatabaseBridge.m
//  iosApp
//
//  Created by Alexander Kormanovsky on 03.04.2021.
//

#import "TDTestDatabaseBridge.h"
#import "TestDatabase.hpp"

@implementation TDProject
@end


@implementation TDTestDatabaseBridge

+ (void)createProjectsWithDatabasePath:(NSString *)databasePath
{
    test::TestDatabase([databasePath UTF8String]).createProjects(100000);
}

+ (NSArray<TDProject *> *)fetchProjectsWithDatabasePath:(NSString *)databasePath
{
    auto projects = test::TestDatabase([databasePath UTF8String]).fetchProjects();
    NSMutableArray *result = [NSMutableArray new];
    
    for (auto &proj : projects) {
        TDProject *project = [TDProject new];
        project.projectId = proj.id;
        project.name = [NSString stringWithUTF8String:proj.name.c_str()];
        project.created = proj.created;
        project.updateTime = proj.updateTime;
        project.isActive = proj.isActive;
        [result addObject:project];
    }

    return result;
}

@end

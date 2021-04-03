//
//  TestDatabaseBridge.m
//  iosApp
//
//  Created by Alexander Kormanovsky on 03.04.2021.
//

#import <Foundation/Foundation.h>
#import "TestDatabaseBridge.h"
#import "TestDatabase.hpp"

using namespace test;

@implementation TestDatabaseBridge

+ (void)createProjectsWithDatabasePath:(NSString *)databasePath
{
    TestDatabase([databasePath UTF8String]).createProjects();
}

+ (NSArray *)fetchProjectsWithDatabasePath:(NSString *)databasePath
{
    auto projects = TestDatabase([databasePath UTF8String]).fetchProjects();
    
    NSMutableArray *result = [NSMutableArray new];
    
    return result;
}

@end

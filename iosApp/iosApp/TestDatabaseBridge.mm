//
//  TDTestDatabaseBridge.m
//  iosApp
//
//  Created by Alexander Kormanovsky on 03.04.2021.
//

#import <Foundation/Foundation.h>
#import "TDTestDatabaseBridge.h"
#import "TestDatabase.hpp"

@implementation TDProject
@end


@implementation TDTestDatabaseBridge

+ (void)createProjectsWithDatabasePath:(NSString *)databasePath
{
    test::TestDatabase([databasePath UTF8String]).createProjects();
}

+ (NSArray *)fetchProjectsWithDatabasePath:(NSString *)databasePath
{
    auto projects = test::TestDatabase([databasePath UTF8String]).fetchProjects();
    
    NSMutableArray *result = [NSMutableArray new];
    TDProject *project = [TDProject new];
    
    return result;
}

@end

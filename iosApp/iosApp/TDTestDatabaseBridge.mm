//
//  TDTestDatabaseBridge.m
//  iosApp
//
//  Created by Alexander Kormanovsky on 03.04.2021.
//

#import "TDTestDatabaseBridge.h"
#import "TestDatabase.hpp"
#import <vector>

@implementation TDProject
@end


@implementation TDTestDatabaseBridge

+ (void)createProjectsWithDatabasePath:(NSString *)databasePath count:(NSInteger)count
{
    test::TestDatabase([databasePath UTF8String]).createProjects((int) count);
}

+ (void)createProjectsWithDatabasePath:(NSString *)databasePath
                                 count:(NSInteger)count
                             imageData:(NSData *)imageData
{
    auto buffer = static_cast<char *>(const_cast<void *>(imageData.bytes));
    auto bufferLength = imageData.length;
    auto _imageData = std::vector<signed char>(buffer, buffer + bufferLength);
    
    test::TestDatabase([databasePath UTF8String]).createImageProjects((int) count, _imageData);
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
        project.imageData = [NSData dataWithBytesNoCopy:proj.image.data() length:proj.image.size()];
        
        [result addObject:project];
    }

    return result;
}

@end

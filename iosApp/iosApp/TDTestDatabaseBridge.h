//
//  TDTestDatabaseBridge.h
//  iosApp
//
//  Created by Alexander Kormanovsky on 03.04.2021.
//

#ifndef TestDatabaseBridge_h
#define TestDatabaseBridge_h

#import <Foundation/Foundation.h>

@interface TDProject : NSObject

@property (nonatomic) NSInteger projectId;
@property (nonatomic) NSString *name;
@property (nonatomic) NSInteger created;
@property (nonatomic) NSInteger updateTime;
@property (nonatomic) BOOL isActive;

@end

@interface TDTestDatabaseBridge : NSObject

+ (void)createProjectsWithDatabasePath:(NSString *)databasePath;
+ (NSArray<TDProject *> *)fetchProjectsWithDatabasePath:(NSString *)databasePath;

@end


#endif /* TestDatabaseBridge_h */

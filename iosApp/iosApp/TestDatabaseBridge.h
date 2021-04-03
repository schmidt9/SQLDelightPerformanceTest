//
//  TDTestDatabaseBridge.h
//  iosApp
//
//  Created by Alexander Kormanovsky on 03.04.2021.
//

#ifndef TestDatabaseBridge_h
#define TestDatabaseBridge_h

@interface TDProject : NSObject

@property (nonatomic) NSInteger projectId;
@property (nonatomic) NSString *name;
@property (nonatomic) NSInteger created;
@property (nonatomic) NSInteger updateTime;
@property (nonatomic) BOOL isActive;

@end

@interface TDTestDatabaseBridge : NSObject

@end


#endif /* TestDatabaseBridge_h */

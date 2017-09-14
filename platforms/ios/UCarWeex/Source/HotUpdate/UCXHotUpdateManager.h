//
//  UCXHotUpdateManager.h
//  Pods
//
//  Created by huyujin on 2017/8/23.
//

#import <Foundation/Foundation.h>

@interface UCXHotUpdateManager : NSObject

- (BOOL)createDir:(NSString *)dir;
- (BOOL)deleteDir:(NSString *)dir;


- (void)unzipFileAtPath:(NSString *)path
          toDestination:(NSString *)destination
        progressHandler:(void (^)(NSString *entry, long entryNumber, long total))progressHandler
      completionHandler:(void (^)(NSString *path, BOOL succeeded, NSError *error))completionHandler;

@end

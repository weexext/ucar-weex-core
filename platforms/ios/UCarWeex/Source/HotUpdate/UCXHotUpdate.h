//
//  UCXHotUpdate.h
//  Pods
//
//  Created by huyujin on 2017/8/23.
//

#import <Foundation/Foundation.h>
#import "UCXDefine.h"

@interface UCXHotUpdate : NSObject

+ (instancetype)shared;

/** 更新包下载解压校验
        mode:
         test[测试]
         pre[预生产]
         production[生产]
 parameters:参数信息
 */
+ (void)hotUpdate:(NSDictionary *)options
         callback:(void (^)(NSError *error))callback;

/** 解析本地包
 *   options:预留字段，可传 nil or @{}
 */
+ (void)unpack:(NSDictionary *)options callback:(void (^)(NSError *error))callback;

@end

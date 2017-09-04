/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

#import "UCImgLoaderDefaultImpl.h"
#import <SDWebImage/SDWebImageManager.h>

#import "UCImgLocalLoader.h"
#import <UCarWeex/UCarWeex.h>

@interface UCImgLoaderDefaultImpl()

//@property (WXDispatchQueueSetterSementics, nonatomic) dispatch_queue_t ioQueue;

@end

@implementation UCImgLoaderDefaultImpl

#pragma mark -
#pragma mark WXImgLoaderProtocol

- (id<WXImageOperationProtocol>)downloadImageWithURL:(NSString *)url imageFrame:(CGRect)imageFrame userInfo:(NSDictionary *)userInfo completed:(void(^)(UIImage *image,  NSError *error, BOOL finished))completedBlock {
    NSString *URLPrefix = @"assets:///image/";
    // 若是处于调试状态
    if ([UCXDebugTool isDebug]) {
        if ([url hasPrefix:URLPrefix]) {
            NSString *imgName = [url substringFromIndex:URLPrefix.length];
            NSString *imgPath = [NSString stringWithFormat:@"%@/%@",[UCXAppConfiguration imagePath],imgName];
            url = imgPath;
        }
    }
    if ([url hasPrefix:@"file:///"] || [url hasPrefix:@"assets:///image/"]) { //处理本地图片
        
        return [UCImgLocalLoader loadLocalImge:url completed:^(UIImage *image, NSError *error, BOOL finished) {
            completedBlock(image, error, finished);
        }];
        
    }else { //处理网络图片
        if ([url hasPrefix:@"//"]) {
            url = [@"http:" stringByAppendingString:url];
        }
        return (id<WXImageOperationProtocol>) [[SDWebImageManager sharedManager] loadImageWithURL:[NSURL URLWithString:url] options:0 progress:^(NSInteger receivedSize, NSInteger expectedSize, NSURL * _Nullable targetURL) {
            
        } completed:^(UIImage * _Nullable image, NSData * _Nullable data, NSError * _Nullable error, SDImageCacheType cacheType, BOOL finished, NSURL * _Nullable imageURL) {
            if (completedBlock) {
                completedBlock(image, error, finished);
            }
        }];
    }

}
@end

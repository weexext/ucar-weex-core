//
//  UCImgLocalLoader.m
//  Portal
//
//  Created by huyujin on 2017/5/17.
//  Copyright © 2017年 ucarinc. All rights reserved.
//

#import "UCXImgLocalLoader.h"

@implementation UCXImgLocalLoader

- (void)cancel {
    
}

+ (id<WXImageOperationProtocol>)loadLocalImge:(NSString *)imgURL completed:(void(^)(UIImage *image,  NSError *error, BOOL finished))completedBlock {
    UCXImgLocalLoader *imgLocalLoader = [UCXImgLocalLoader new];
    
    UIImage *img = nil;
    if ([imgURL hasPrefix:@"file:///local:"]) { //项目中图片
        NSString *imgName = [imgURL substringFromIndex:14];
        img = [UIImage imageNamed:imgName];
    } else { // 本地路径中图片
        NSString *imgName = [imgURL substringFromIndex:8];
        img = [UIImage imageWithContentsOfFile:imgName];
    }
    if (img) {
        completedBlock(img,nil,YES);
    } else {
        NSError *error = [NSError errorWithDomain:NSCocoaErrorDomain code:-1 userInfo:@{}];
        completedBlock(nil,error,YES);
    }
    return imgLocalLoader;
}

@end

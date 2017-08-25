//
//  UCXAppConfiguration.m
//  Pods
//
//  Created by huyujin on 2017/8/25.
//
//

#import "UCXAppConfiguration.h"
#import "UCXUtil.h"

@implementation UCXAppConfiguration

+ (NSString *)jsBundlePath {
    //downloadDir+filename+jsBundle
    NSString *jsBundlePath = [NSString stringWithFormat:@"%@/%@",UCXDownloadDir];
    return @"";
}

@end

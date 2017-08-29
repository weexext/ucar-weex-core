//
//  UCXAppConfiguration.h
//  Pods
//
//  Created by huyujin on 2017/8/25.
//
//

#import <Foundation/Foundation.h>

@interface UCXAppConfiguration : NSObject

/**
 * cache path
    "../../"
 */
+ (NSString *)cachePath;

/**
 * @abstract  bundlejs path
    "../../jsBundle/"
 */
+ (NSString *)jsBundlePath;

/** image path
 * "../../res/image/"
 */
+ (NSString *)imagePath;


@end

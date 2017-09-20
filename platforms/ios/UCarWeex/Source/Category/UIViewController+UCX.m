//
//  ViewController+UCX.m
//  CYLTabBarController
//
//  Created by huyujin on 2017/9/19.
//

#import "UIViewController+UCX.h"
#import <objc/runtime.h>

static void *ucxOptionsKey = &ucxOptionsKey;

@implementation UIViewController(UCX)

-(void)setUcx_options:(NSString *)options
{
    objc_setAssociatedObject(self, &ucxOptionsKey, options, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

-(NSString *)ucx_options
{
    return objc_getAssociatedObject(self, &ucxOptionsKey);
}


@end

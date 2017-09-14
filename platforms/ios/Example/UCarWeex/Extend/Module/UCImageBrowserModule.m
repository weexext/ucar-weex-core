//
//  UCBrowserImageModule.m
//  Portal
//
//  Created by huyujin on 2017/6/7.
//  Copyright © 2017年 ucarinc. All rights reserved.
//

#import "UCImageBrowserModule.h"

#import <IDMPhotoBrowser/IDMPhotoBrowser.h>
#import "AppDelegate.h"

@implementation UCImageBrowserModule
@synthesize weexInstance;

WX_EXPORT_METHOD(@selector(browserImages:selectedIndex:))


- (void)browserImages:(NSArray<NSString*> *)imgs selectedIndex:(NSUInteger)selectedIndex
{
    AppDelegate *delegate =  (AppDelegate * )[UIApplication sharedApplication].delegate;
    WXRootViewController *rootViewController = (WXRootViewController *) delegate.window.rootViewController;
    NSMutableArray *photos = [NSMutableArray new];
    for (NSString *img in imgs) {
        NSString *url = img;
        if ([url hasPrefix:@"//"]) {
            url = [NSString stringWithFormat:@"http:%@", url];
        }
        if ([url hasPrefix:@"http"]) {
            IDMPhoto *photo = [IDMPhoto photoWithURL:[NSURL URLWithString:url]];
            [photos addObject:photo];
        }else if ([url hasPrefix:@"file:///"]){
            NSString *imgURL = [url substringFromIndex:8];
            IDMPhoto *photo = [IDMPhoto photoWithFilePath:imgURL];
            [photos addObject:photo];
        }
    }
    if (photos.count > 0) {
        IDMPhotoBrowser *browser = [[IDMPhotoBrowser alloc] initWithPhotos:photos];
        [browser setInitialPageIndex:selectedIndex];
        browser.dismissOnTouch = true;
        browser.usePopAnimation = true;
        browser.displayArrowButton = true;
        browser.displayCounterLabel = true;
        browser.displayDoneButton = false;
        [rootViewController presentViewController:browser animated:YES completion:nil];
    }
    
}

@end

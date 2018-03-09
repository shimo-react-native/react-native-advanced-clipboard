#import "RNAdvancedClipboard.h"
#import <UIKit/UIKit.h>

@implementation RNAdvancedClipboard

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(setString:(NSString *)content)
{
  UIPasteboard *clipboard = [UIPasteboard generalPasteboard];
  clipboard.string = (content ? : @"");
}

RCT_EXPORT_METHOD(getString:(RCTPromiseResolveBlock)resolve
                  rejecter:(__unused RCTPromiseRejectBlock)reject)
{
  UIPasteboard *clipboard = [UIPasteboard generalPasteboard];
  resolve((clipboard.string ? : @""));
}

RCT_EXPORT_METHOD(getContent:(RCTPromiseResolveBlock)resolve
                  rejecter:(__unused RCTPromiseRejectBlock)reject)
{
  UIPasteboard *clipboard = [UIPasteboard generalPasteboard];
  resolve(@{
            @"text": clipboard.string ? : @"",
            @"changeCount": @(clipboard.changeCount),
            @"unequalId": @(clipboard.changeCount)
            });
}

@end

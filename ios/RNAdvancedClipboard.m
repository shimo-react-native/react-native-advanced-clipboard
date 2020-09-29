#import "RNAdvancedClipboard.h"
#import <UIKit/UIKit.h>

@interface RNAdvancedClipboard()

@property (nonatomic, assign) NSInteger changeCount;
@property (nonatomic, copy) NSString *string;

@end

@implementation RNAdvancedClipboard

- (instancetype)init {
    self = [super init];
    if (self) {
        _changeCount = -1;
    }
    return self;
}

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setString:(NSString *)content)
{
    UIPasteboard *clipboard = [UIPasteboard generalPasteboard];
    clipboard.string = (content ? : @"");
}

RCT_EXPORT_METHOD(getString:(RCTPromiseResolveBlock)resolve
                  rejecter:(__unused RCTPromiseRejectBlock)reject)
{
    [self updateClipboard];
    resolve((_string ? : @""));
}

RCT_EXPORT_METHOD(getContent:(RCTPromiseResolveBlock)resolve
                  rejecter:(__unused RCTPromiseRejectBlock)reject)
{
    [self updateClipboard];
    resolve(@{
        @"text": _string ? : @"",
        @"changeCount": @(_changeCount)
            });
}

- (void)updateClipboard {
    UIPasteboard *clipboard = [UIPasteboard generalPasteboard];
    if (_changeCount != clipboard.changeCount) {
        _changeCount = clipboard.changeCount;
        _string = clipboard.string;
    }
}

@end

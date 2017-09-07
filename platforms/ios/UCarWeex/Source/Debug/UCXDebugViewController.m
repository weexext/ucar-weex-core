//
//  UCXDebugViewController.m
//  Pods
//
//  Created by huyujin on 2017/9/4.
//
//

#import "UCXDebugViewController.h"

#import "UCXDefine.h"

#define UCX_IP_REGEX @"(?:[0-9]{1,3}\.){3}[0-9]{1,3}"

@interface UCXDebugViewController ()<UITextFieldDelegate>

@property (nonatomic, strong) UIView *debugView;
@property (nonatomic, strong) UIView *weexDebugView;
@property (nonatomic, strong) UIView *weexDebugIPView;

@property (nonatomic, strong) UIView *remoteDebugView;
@property (nonatomic, strong) UIView *remoteDebugIPView;

@property (nonatomic, strong) NSMutableDictionary *debugInfoDict;

@end

@implementation UCXDebugViewController

- (instancetype)initWithFrame:(CGRect )frame
{
    if ((self = [super init])) {
        self.view.frame = frame;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.view.backgroundColor = [UIColor colorWithRed:230/255.f green:230.f/255.f blue:230.f/255.f alpha:1.f];
    self.navigationItem.title = @"调试设置";
    //
    UIBarButtonItem *rightBtnItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(saveDebugInfo)];
    self.navigationItem.rightBarButtonItem = rightBtnItem;
    
    CGRect rect = [UIScreen mainScreen].bounds;
    self.view.frame =  CGRectMake(0, 0, rect.size.width, rect.size.height);
    
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(closeKeyboard)];
    [self.view addGestureRecognizer:tap];
    
    [self configData];
    [self configViews];
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self.navigationController setNavigationBarHidden:NO animated:YES];
}

-(void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    [self.navigationController setNavigationBarHidden:YES animated:YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - 
- (void)configData {
    self.debugInfoDict = [NSMutableDictionary dictionary];
    //
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSDictionary *dict = [userDefaults objectForKey:UCX_US_UCAR_WEEX_DEBUG_KEY];
    if (dict && [dict count]>0) {
        self.debugInfoDict = [dict mutableCopy];
    }
}

- (void)saveDebugInfo {
    [self closeKeyboard];
    //
    NSString *weexDebugIP = [self.debugInfoDict objectForKey:@"weexDebugIP"];
    
    BOOL isWeexDebug = [self.debugInfoDict objectForKey:@"isWeexDebug"] && [[self.debugInfoDict objectForKey:@"isWeexDebug"] isEqualToString:@"true"];
    if (isWeexDebug) {
        if (!weexDebugIP || ![self validateIP:weexDebugIP]) {
            [self alertMsg:@"WEEX调试IP格式不正确"];
            return;
        }else {
            NSString *weexDebugUrl = [self.debugInfoDict objectForKey:@"weexDebugUrl"];
            //
            NSError *error = nil;
            NSRegularExpression *regex = [NSRegularExpression regularExpressionWithPattern:UCX_IP_REGEX options:NSRegularExpressionCaseInsensitive error:nil];
            NSString *modifiedStr = [regex stringByReplacingMatchesInString:weexDebugUrl options:0 range:NSMakeRange(0, [weexDebugUrl length]) withTemplate:weexDebugIP];
            [self.debugInfoDict setObject:modifiedStr forKey:@"weexDebugUrl"];
        }
    }
    BOOL isRemote = [self.debugInfoDict objectForKey:@"isRemote"] && [[self.debugInfoDict objectForKey:@"isRemote"] isEqualToString:@"true"];
    if (isRemote) {
        NSString *webIP = [self.debugInfoDict objectForKey:@"webIP"];
        if (!webIP || ![self validateIP:webIP]) {
            [self alertMsg:@"远程加载IP格式不正确"];
        }else {
            NSString *webUrl = [self.debugInfoDict objectForKey:@"webUrl"];
            NSRegularExpression *regex = [NSRegularExpression regularExpressionWithPattern:UCX_IP_REGEX options:NSRegularExpressionCaseInsensitive error:nil];
            NSString *modifiedStr = [regex stringByReplacingMatchesInString:webUrl options:0 range:NSMakeRange(0, [webUrl length]) withTemplate:webIP];
            [self.debugInfoDict setObject:modifiedStr forKey:@"webUrl"];
        }
    }
    //
    [[NSUserDefaults standardUserDefaults]  setObject:self.debugInfoDict forKey:UCX_US_UCAR_WEEX_DEBUG_KEY];
    //保存成功
    [self alertMsg:@"保存成功O(∩_∩)O~~，\n记得[退出->重新打开]~~"];
    
}

- (void)configViews {
    [self configDebugView];
    [self configWeexDebugView];
    [self configRemoteDebugView];
}

- (void)configDebugView {
    //调试模式...
    UIView *debugView = [[UIView alloc] init];
    self.debugView = debugView;
    [self.view addSubview:debugView];
    CGFloat yPosition = 74.f;
    debugView.frame = CGRectMake(0, yPosition, self.view.frame.size.width, 45);
    debugView.backgroundColor = [UIColor whiteColor];
    //
    UILabel *titleLabel = [self configLabelWithParent:debugView text:@"调试模式"];
    //
    UISwitch *theSwitch = [[UISwitch alloc] init];
    
    CGFloat switchOriginY = round((debugView.frame.size.height - theSwitch.frame.size.height) / 2.0);
    CGFloat switchOriginX = CGRectGetMaxX(debugView.frame) - theSwitch.frame.size.width-8;
    theSwitch.frame = CGRectMake(switchOriginX, switchOriginY, theSwitch.frame.size.width, theSwitch.frame.size.height);
    [debugView addSubview:theSwitch];
    
    [theSwitch addTarget:self action:@selector(oneSwitchValueChanged:) forControlEvents:UIControlEventValueChanged];
    theSwitch.tag = 101;
    BOOL isDebug = [self.debugInfoDict objectForKey:@"isDebug"] && [[self.debugInfoDict objectForKey:@"isDebug"] isEqualToString:@"true"];
    theSwitch.on = isDebug;
}

- (void)configWeexDebugView {
    //weex调试模式
    UIView *weexDebugView = [[UIView alloc] init];
    self.weexDebugView = weexDebugView;
    [self.view addSubview:weexDebugView];
    CGFloat yPosition = self.debugView.frame.origin.y+self.debugView.frame.size.height+8;
    weexDebugView.frame = CGRectMake(0, yPosition, self.view.frame.size.width, 45);
    weexDebugView.backgroundColor = [UIColor whiteColor];
    
    UILabel *titleLabel = [self configLabelWithParent:weexDebugView text:@"WEEX调试模式"];

    UISwitch *theSwitch = [[UISwitch alloc] init];
    CGFloat switchOriginY = round((weexDebugView.frame.size.height - theSwitch.frame.size.height) / 2.0);
    CGFloat switchOriginX = CGRectGetMaxX(weexDebugView.frame) - theSwitch.frame.size.width-8;
    theSwitch.frame = CGRectMake(switchOriginX, switchOriginY, theSwitch.frame.size.width, theSwitch.frame.size.height);
    [weexDebugView addSubview:theSwitch];
    
    [theSwitch addTarget:self action:@selector(oneSwitchValueChanged:) forControlEvents:UIControlEventValueChanged];
    theSwitch.tag = 102;
    BOOL isWeexDebug = [self.debugInfoDict objectForKey:@"isWeexDebug"] && [[self.debugInfoDict objectForKey:@"isWeexDebug"] isEqualToString:@"true"];
    theSwitch.on = isWeexDebug;
    
    //weexdebugIP
    UIView *weexDebugIPView = [[UIView alloc] init];
    self.weexDebugIPView = weexDebugIPView;
    [self.view addSubview:weexDebugIPView];
    yPosition = self.weexDebugView.frame.origin.y+self.weexDebugView.frame.size.height;
    weexDebugIPView.frame = CGRectMake(0, yPosition, self.view.frame.size.width, 45);
    weexDebugIPView.backgroundColor = [UIColor whiteColor];
    
    UILabel *weexDebugIPLabel = [self configLabelWithParent:weexDebugIPView text:@"WEEX调试IP"];
    
    NSString *weexDebugIP = [self.debugInfoDict objectForKey:@"weexDebugIP"];
    UITextField *textField = [self configTextFieldWithParent:weexDebugIPView tag:201 text:weexDebugIP];
    
}

- (void)configRemoteDebugView {
    //weex调试模式
    UIView *remoteDebugView = [[UIView alloc] init];
    self.remoteDebugView = remoteDebugView;
    [self.view addSubview:remoteDebugView];
    CGFloat yPosition = self.weexDebugIPView.frame.origin.y+self.weexDebugIPView.frame.size.height+8;
    remoteDebugView.frame = CGRectMake(0, yPosition, self.view.frame.size.width, 45);
    remoteDebugView.backgroundColor = [UIColor whiteColor];
    
    UILabel *titleLabel = [self configLabelWithParent:remoteDebugView text:@"远程加载模式"];
    
    UISwitch *theSwitch = [[UISwitch alloc] init];
    CGFloat switchOriginY = round((remoteDebugView.frame.size.height - theSwitch.frame.size.height) / 2.0);
    CGFloat switchOriginX = CGRectGetMaxX(remoteDebugView.frame) - theSwitch.frame.size.width-8;
    theSwitch.frame = CGRectMake(switchOriginX, switchOriginY, theSwitch.frame.size.width, theSwitch.frame.size.height);
    [remoteDebugView addSubview:theSwitch];
    
    [theSwitch addTarget:self action:@selector(oneSwitchValueChanged:) forControlEvents:UIControlEventValueChanged];
    theSwitch.tag = 103;
    BOOL isRemoteDebug = [self.debugInfoDict objectForKey:@"isRemote"] && [[self.debugInfoDict objectForKey:@"isRemote"] isEqualToString:@"true"];
    theSwitch.on = isRemoteDebug;
    
    //weexdebugIP
    UIView *remoteDebugIPView = [[UIView alloc] init];
    self.remoteDebugIPView = remoteDebugIPView;
    [self.view addSubview:remoteDebugIPView];
    yPosition = self.remoteDebugView.frame.origin.y+self.remoteDebugView.frame.size.height;
    remoteDebugIPView.frame = CGRectMake(0, yPosition, self.view.frame.size.width, 45);
    remoteDebugIPView.backgroundColor = [UIColor whiteColor];
    
    UILabel *remoteDebugIPLabel = [self configLabelWithParent:remoteDebugIPView text:@"远程加载IP"];
    
    NSString *webIP = [self.debugInfoDict objectForKey:@"webIP"];
    UITextField *textField = [self configTextFieldWithParent:remoteDebugIPView tag:202 text:webIP];
}

- (void)oneSwitchValueChanged:(UISwitch *)sender {
    BOOL isOn = sender.isOn;
    switch (sender.tag) {
        case 101:
        {
            NSString *isDebugStr = isOn ? @"true":@"false";
            [self.debugInfoDict setObject:isDebugStr forKey:@"isDebug"];
        }
            break;
        case 102:
        {
            NSString *isWeexDebugStr = isOn ? @"true":@"false";
            [self.debugInfoDict setObject:isWeexDebugStr forKey:@"isWeexDebug"];
        }
            break;
        case 103:
        {
            NSString *isRemoteStr = isOn ? @"true":@"false";
            [self.debugInfoDict setObject:isRemoteStr forKey:@"isRemote"];
        }
            break;
        default:
            break;
    }
}

#pragma mark - text field delegate 
- (void)textFieldDidEndEditing:(UITextField *)textField {
    switch (textField.tag) {
        case 201: //weex debug ip
        {
            NSString *weexDebugIP = [textField.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
            [self.debugInfoDict setObject:weexDebugIP forKey:@"weexDebugIP"];
        }
            break;
        case 202: //remote debug ip
        {
            NSString *webIP = [textField.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
            [self.debugInfoDict setObject:webIP forKey:@"webIP"];
        }
            break;
        default:
            break;
    }
}

#pragma mark - private method
- (UILabel *)configLabelWithParent:(UIView *)parent text:(NSString *)text {
    UILabel *titleLabel = [[UILabel alloc] init];
    [parent addSubview:titleLabel];
    titleLabel.frame = CGRectMake(8, 12, parent.frame.size.width/2, 20);
    titleLabel.font = [UIFont systemFontOfSize:14.f];
    titleLabel.text = text;
    titleLabel.textColor = [UIColor blackColor];
    titleLabel.textAlignment = NSTextAlignmentLeft;
    return titleLabel;
}

- (UITextField *)configTextFieldWithParent:(UIView *)parent tag:(NSInteger)tag text:(NSString *)text {
    UITextField *textField = [[UITextField alloc] init];
    [parent addSubview:textField];
    textField.frame = CGRectMake(parent.frame.size.width/2, 12, parent.frame.size.width/2-8, 24);
    textField.textAlignment = NSTextAlignmentLeft;
    textField.font = [UIFont systemFontOfSize:15.f];
    textField.borderStyle = UITextBorderStyleRoundedRect;
    
    textField.delegate = self;
    textField.tag = tag;
    textField.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    //
    textField.text = text;
    
    return textField;
}

- (void)closeKeyboard {
    [[UIApplication sharedApplication].keyWindow endEditing:YES];
}

/**
 *  利用正则表达式验证
 *
 *  @param ip
 */
- (BOOL)validateIP:(NSString *)ip {
    NSString *ipRegex = UCX_IP_REGEX;
    NSPredicate *ipPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", ipRegex];
    return [ipPredicate evaluateWithObject:ip];
}

- (void)alertMsg:(NSString *)msg {
    // 1.创建UIAlertController
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@""
                                                                             message:msg
                                                                      preferredStyle:UIAlertControllerStyleAlert];
    // 2.创建并添加按钮
    UIAlertAction *okAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        
    }];
    [alertController addAction:okAction];
    // 3.呈现UIAlertContorller
    [self presentViewController:alertController animated:YES completion:nil];
}


@end

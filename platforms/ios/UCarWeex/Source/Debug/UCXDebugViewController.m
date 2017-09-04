//
//  UCXDebugViewController.m
//  Pods
//
//  Created by huyujin on 2017/9/4.
//
//

#import "UCXDebugViewController.h"
#import "UCXDebugTableViewCell.h"
#import "UCXDefine.h"

@interface UCXDebugViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (strong,nonatomic) UITableView *table;

@property (nonatomic, strong) NSMutableArray *ipArr;

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
    self.view.backgroundColor = [UIColor whiteColor];
    self.navigationItem.title = @"调试设置";
    //
    UIBarButtonItem *rightBtnItem = [[UIBarButtonItem alloc] initWithTitle:@"Add" style:UIBarButtonItemStylePlain target:self action:@selector(addIP)];
    self.navigationItem.rightBarButtonItem = rightBtnItem;
    
    CGRect rect = [UIScreen mainScreen].bounds;
    self.view.frame =  CGRectMake(0, 0, rect.size.width, rect.size.height);
    
    [self configData];
    [self cofigureTableview];
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
    self.ipArr = [NSMutableArray array];
    //
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSDictionary *debugInfo = [userDefaults objectForKey:UCX_US_UCAR_WEEX_DEBUG_KEY];
    if (debugInfo && [debugInfo count]>0) {
        NSArray *ipArr = [debugInfo objectForKey:@"ipArr"];
        if (ipArr && [ipArr count]>0) {
            self.ipArr = [ipArr mutableCopy];
        }
    }
}

-(void)cofigureTableview
{
    self.table = [[UITableView alloc] initWithFrame:CGRectMake(self.view.bounds.origin.x, 0, self.view.bounds.size.width, self.view.bounds.size.height-20) style:UITableViewStylePlain];
    self.table.delegate = self;
    self.table.dataSource = self;
    [self.view addSubview:self.table];
    [self.table  setTableFooterView:[[UIView alloc] initWithFrame:CGRectZero]];
    
}


#pragma mark - 

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.ipArr count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"cellIdentifier";
    
    UCXDebugTableViewCell *cell = [self.table dequeueReusableCellWithIdentifier:cellIdentifier];
    
    if(cell == nil) {
        cell = [[UCXDebugTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    NSInteger row = [indexPath row];
    [cell config:self.ipArr[row]];
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 44;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath;
{
//    NSLog(@"title of cell %@", [_content objectAtIndex:indexPath.row]);
}


#pragma mark - 
- (void)addIP {
    UCXLog(@"addIP");
    
    [self alertIP];
}

- (void)alertIP {
    __weak typeof(self) weakSelf = self;
    
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"添加新的IP" message:@"添加成功后须关闭并重新启动应用" preferredStyle:UIAlertControllerStyleAlert];
    [alertController addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.placeholder = @"填写IP";
//        textField.secureTextEntry = YES;
    }];
    UIAlertAction *confirmAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        NSLog(@"Current IP %@", [[alertController textFields][0] text]);
        //compare the current password and do action here
        NSString *ip = [[alertController textFields][0] text];
        ip = [ip stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
        //
        [weakSelf.ipArr insertObject:ip atIndex:0];
        [weakSelf.table reloadData];
    }];
    [alertController addAction:confirmAction];
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"Cancel" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
        NSLog(@"Canelled");
    }];
    [alertController addAction:cancelAction];
    [self presentViewController:alertController animated:YES completion:nil];
}

@end

#include<stdio.h>
#include<Windows.h>
#include<string.h>
int number[100], num1, cart_num = 0;

//商品信息和购物车信息
typedef struct All_IFN {
    char name[100];
    int num;
    double price;
} ZL;
typedef struct SHOP_IFN{
    char name[100];
    int num;
    double price;
} SP;

ZL NAME[100] = {};
SP SHOP[100] = {};

//这里是我加个'\0'的函数，因为编译器会说输入的可能缺失结束符，顺便加个而已
void TOOL_SUFFIX(char shuzu[])
{
    int strlong = (int)strlen(shuzu);
    /*这是因为“size_t"是无符号整数类型，而“int"是有符号整数类型。
    当将无符号整数类型转换为有符号整数类型时，可能会导致数据丢失
    所以加一个强制转换*/
    shuzu[strlong] = '\0';
}
//创建物品信息（包含续添加）
void create_ifn()
{
    system("cls");
    FILE* fp = fopen("NAME", "r");
    char name[100];
    printf("输入信息\n\n");
    for(int num = 0; num < 4; num++)
    {
        printf("名称：");
        scanf("%s", name);
        printf("数量：");
        scanf("%d", &NAME[num].num);
        printf("价格：");
        scanf("%lf", &NAME[num].price);
        printf("\n");
    }
    if ((fp = fopen("NAME", "a")) == NULL)
    {
        printf("写入文件失败");
        return;
    }
    fwrite(NAME, sizeof(ZL), 4, fp);//将前面数组里面的数据写入到文件里，以防止程序结束，数据丢失
    fclose(fp);
    return;
}
//展示物品信息
void show()
{
    system("cls");
    FILE* fp = fopen("NAME", "r");
    printf("\n信息展示\n");
    for (int i = 0; (fread(&NAME[i], sizeof(ZL), 1, fp)) != 0; i++)
    {
        printf("-------------------------------\n");
        printf("名称\t数量\t价格\n");
        printf("%s\t%d\t%.2f元", NAME[i].name, NAME[i].num, NAME[i].price);
        printf("\n");
    }
    fclose(fp);
}
//展示已经加入购物车的物品
void show_cart()
{
    system("cls");
    FILE* fp1 = fopen("SHOP", "r");
    for (int i = 0; fread(&SHOP[i], sizeof(SP), 1, fp1) != 0; i++)
    {
        printf("-------------------------------\n");
        printf("名称\t数量\t价格\n");
        printf("%s\t%d\t%.2f元", SHOP[i].name, SHOP[i].num, SHOP[i].price);
        printf("\n");
    }
    fclose(fp1);
}
//购物车物品添加
void add_cart()
{
    int cart_in = 0;
    int cart_in_sp = 0;
    FILE* fp1 = fopen("SHOP", "r");
    FILE* fp = fopen("NAME", "r");
    printf("\n");
    //这里三个是为了检测文件是否打开成功
    if (fp1 == nullptr)
    {
        printf("\nfp1文件打开失败\n");
    }
    if (fp == nullptr)
    {
        printf("fp文件打开失败\n");
    }
    if (fp == NULL)//判断文件是否为空
    {
        printf("文件出现错误，请检查代码部分(或者当前库存中无物品，请自行退出)\n");
        return;
    }
    while (1)
    {
        bool CT = false;
        char name[100];
        //将文件数据存储到数组当中来
        if(fp1 != nullptr)
        {
            while (fread(&SHOP[cart_in_sp], sizeof(SP), 1, fp1))
            {
                cart_in_sp++;
            }
        }
        while (fread(&NAME[cart_in], sizeof(SP), 1, fp) == 1)
        {   
            cart_in++;
        }
        fclose(fp);
        fclose(fp1);
        printf("输入所需要的商品：  ");
        scanf("%s", name);
        for (int i = 0; i < cart_in; i++)//添加进SHOP数组当中去
        {
            //索引商品和数量的添加
            if (strcmp(NAME[i].name, name) == 0 && NAME[i].num != 0)
            {
                printf("已为您找到该商品\n");
                printf("-------------------------------\n");
                printf("名称\t数量\t价格\n");
                printf("%s\t%d\t%.2f元", NAME[i].name, NAME[i].num, NAME[i].price);
                printf("\n");
                strcpy(SHOP[cart_in_sp].name, name);
                while (1)
                {
                    int num;
                    printf("输入您需要的数量：");
                    scanf("%d", &num);
                    getchar();
                    if (num > NAME[i].num)
                    {
                        printf("库存不足，请重新输入\n");
                        continue;
                    }
                    else
                    {
                        SHOP[cart_in_sp].num = num;
                        SHOP[cart_in_sp].price = NAME[i].price;
                        NAME[i].num -= num;
                        cart_in_sp++;
                        CT = true;
                        printf("已为您记录\n");
                        break;
                    }
                }
            }
        }
        //判断是否有输入商品
        if (!CT)
        {
            printf("当前无此商品，请重新输入\n");
            continue;
        }
        printf("还需要购物吗？（\"Y\" 或者\"N\"）\n");
        char BUER;
        scanf("%c", &BUER);
        if ('Y' == BUER || 'y' == BUER)
        {
            continue;
        }
        else
        {
            fp = fopen("NAME", "w");
            fp1 = fopen("SHOP", "w");
            fwrite(SHOP, sizeof(SP), cart_in_sp, fp1);
            fwrite(NAME, sizeof(ZL), cart_in, fp);
            fclose(fp);
            fclose(fp1);
            return;
        }
    }  
}
//购物车
void shop_car()
{
    system("cls");
    printf("购物车选项：\n");
    while (1)
    {
        printf("\n 1.显示当前购物列表\n 2.添加商品\n 3.退出\n");
        printf("输入对应的数字进行操作\n");
        scanf("%d", &num1);
        printf("\n\n");
        if(num1 <= 3 && num1 > 0)
        {
            system("cls");
            switch (num1)
            {
            case 1:
            {
                FILE* fp1 = fopen("SHOP", "r");
                if (fp1 == NULL || SHOP[0].num == 0)
                {
                    printf("当前无选中物品\n");
                    printf("0");
                    break;
                }
                fclose(fp1);
                printf("购物列表显示\n");
                show_cart();
                break;
            }
            case 2:
                add_cart();
                break;
            case 3:
                return;
                break;
            }
        }
        else
        {
            printf("请输入正确的数字！\n\n");
        }
    }
}
//结算部分
void price_sum()
{
    FILE* fp1 = fopen("SHOP", "r");
    int NUM = 0;
    double PR_SUM = 0;
    //其实这些之所以这样写，是为了保证使用的数据一直是最新的，虽然有点麻烦。
    while (fread(&SHOP[NUM], sizeof(SP), 1, fp1))
    {
        NUM++;
    }
    fclose(fp1);
    for (int i = 0; i < NUM; i++)
    {
        PR_SUM += SHOP[i].price * SHOP[i].num;
    }
    show_cart();
    memset(SHOP, '\0', sizeof(SHOP));
    if (PR_SUM == 0)
    {
        printf("当前购物车中无物品");
        return;
    }
    printf("\n您本次消费了：\t%.2lf元\n", PR_SUM);
    fp1 = fopen("SHOP", "w");
    NUM = 0;
    fwrite(SHOP, sizeof(SP), NUM, fp1);
    fclose(fp1);
}
//程序功能选择
void Start()
{
    printf("*********************************\n");
    printf("\t欢迎来到购物管理系统\n");
    printf("*********************************\n\n\n");
    while (1)
    {
        printf("\n 1.建立信息\n 2.显示信息\n 3.购物车\n 4.结算\n 5.退出\n");
        printf("请输入对应的数字进行操作\n");
        int num1;
        scanf("%d", &num1);
        printf("\n");
        if (num1 <= 5 && num1 > 0)
        {
            switch (num1)
            {
            case 1:
                create_ifn();
                break;
            case 2:
            {
                //这里是判断现在库存里面是否有
                FILE* fp = fopen("NAME", "r");
                if (fp == NULL)
                {
                    system("cls");
                    printf("——当前系统内无商品，请添加商品！——\n");
                    continue;
                }
                show();
                break;
            }
            case 3:
                shop_car();
                break;
            case 4:
                price_sum();
                break;
            case 5:
                printf("好的，祝您购物愉快！");
                return;
            }
        }
        else
        {
            system("cls");
            printf("——提示：请输入正确的数字！——\n");
        }
    }
}
//程序入口
int main()
{
    Start();
    return 0;
}
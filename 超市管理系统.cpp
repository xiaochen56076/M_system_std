#include<stdio.h>
#include<Windows.h>
#include<string.h>
char object[100][100];
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

//这里是我加个'\0'的函数，因为编译器会说输入的可能缺失结束符
void TOOL_SUFFIX(char shuzu[])
{
    int strlong = (int)strlen(shuzu);
    /*这是因为“size_t"是无符号整数类型，而“int"是有符号整数类型。
    当将无符号整数类型转换为有符号整数类型时，可能会导致数据丢失
    所以加一个强制转换*/
    shuzu[strlong] = '\0';
}

//创建物品信息
int create_ifn()
{
    system("cls");
    FILE* fp;
    printf("输入信息\n\n");
    for(int num = 0; num < 4; num++)
    {
        printf("名称：");
        scanf("%s", NAME[num].name);
        printf("数量：");
        scanf("%d", &NAME[num].num);
        printf("价格：");
        scanf("%lf", &NAME[num].price);
        printf("\n");
    }
    if ((fp = fopen("NAME", "a")) == NULL)
    {
        printf("写入文件失败");
        return 0;
    }
    fwrite(NAME, sizeof(ZL), 4, fp);//将前面数组里面的数据写入到文件里，以防止程序结束，数据丢失
    fclose(fp);
    return 0;
}
//展示物品信息
void show()
{
    system("cls");
    FILE* fp = fopen("NAME", "r");
    printf("\n信息展示\n");
    for (int i = 0; (fread(NAME + i, sizeof(ZL), 1, fp)) != 0; i++)
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
    printf("购物列表显示\n");
    for (int i = 0; fread(SHOP + i, sizeof(SP), 1, fp1) != 0; i++)
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
    char  choice = '\0';
    FILE* fp = fopen("NAME", "r");
    FILE* fp1 = fopen("SHOP", "a");
    while (1)
    {
        char name[100];
        printf("输入需要物品的名称：");
        scanf("%s", SHOP[cart_num].name);
        TOOL_SUFFIX(name);
        printf("\n");
        if (fp == NULL)//判断文件是否为空
        {
            printf("文件出现错误，请检查代码部分(或者当前库存中无物品，请自行退出)\n");
            break;
        }
        for (int j = 0; fread(NAME + j, sizeof(ZL), 1, fp) != 0; j++)
        {
            if (strcmp(NAME[j].name, SHOP[cart_num].name) == 0 && NAME[j].num != 0)
            {
                printf("已找到当前物品\n");
                printf("-----------------------------\n");
                printf("名称\t数量\t价格\n");
                printf("%s\t%d\t%f元", NAME[j].name, NAME[j].num, NAME[j].price);
                SHOP[cart_num].price = (double)NAME[j].price;
                while (1)
                {
                    printf("\n");
                    printf("输入需要的数量：");
                    scanf("%d", &SHOP[cart_num].num);
                    if (SHOP[cart_num].num > NAME[j].num)//判断数量是否足够
                    {
                        printf("库存数量不足，请重新输入您需要的数量");
                        continue;
                    }
                    else
                    {
                        system("cls");
                        printf("\n");
                        printf("好的，已为您记录\n\n");
                        break;
                    }
                }
                printf("还需要继续购物吗？（输入Y继续，输入N结束）\n");
                scanf("%*c%c", &choice);
                if (choice == 'N' || choice == 'n')
                {
                    break;
                }
                else if (choice == 'Y' || choice == 'y')
                {
                    cart_num++;
                    continue;
                }
                else
                {
                    printf("请输入正确的字母");
                    continue;
                }
            }
            else
            {
                printf("抱歉，当前无此商品\n");
                memset(name, 0, sizeof(name));
                break;
            }
        }
        if (choice == 'N' || choice == 'n')
        {
            system("cls");
            cart_num += 1;
            fwrite(SHOP, sizeof(SP), cart_num, fp1);
            fclose(fp1);
            fclose(fp);
            break;
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
                if (fp1 == NULL)
                {
                    printf("当前无选中物品\n");
                    printf("0");
                    break;
                }
                fclose(fp1);
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
    FILE* fp = fopen("NAME", "r");
    FILE* fp1 = fopen("SHOP", "r");
    char OB[100];
    double PR_SUM = 0;
    for (int i = 0; fread(SHOP, sizeof(ZL), 1, fp) != 0; i++)
    {
        strcpy(OB, SHOP[i].name);
        for (int j = 0; fread(NAME, sizeof(SP), 1, fp1) != 0; j++)
        {
            if (strcmp(NAME[j].name, OB) == 0)
            {
                PR_SUM += SHOP[j].num * NAME[j].price;
            }
        }
    }
    memset(SHOP, 0, sizeof(SHOP));
    fclose(fp);
    fclose(fp1);
}

//程序入口
int main()
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
        if(num1 <= 5 && num1 > 0)
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
                return 0;
            }
        }
        else
        {
            system("cls");
            printf("——提示：请输入正确的数字！——\n");
        }
    }
    return 0;
}
#include<stdio.h>
#include<Windows.h>
#include<string.h>
char object[100][100];
int number[100], num1, cart_num = 0;

//商品信息和购物车信息
typedef struct All {
    char name[100];
    int num;
    int prince;
} ZL;
typedef struct SHOP
{
    char name[100];
    int num;
    double prince;
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
        scanf("%d", &NAME[num].prince);
        printf("\n");
    }
    if ((fp = fopen("NAME", "a")) == NULL)
    {
        printf("写入文件失败");
        return 0;
    }
    fwrite(NAME, sizeof(ZL), 4, fp);
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
        printf("%s\t%d\t%d元", NAME[i].name, NAME[i].num, NAME[i].prince);
        printf("\n");
    }
    fclose(fp);
}
//展示已经加入购物车的物品
void show_cart()
{
    system("cls");
    FILE* fp1 = fopen("SHOP", "r");
    printf("购物列表显示");
    for (int i = 0; fread(SHOP + i, sizeof(SP), 1, fp1); i++)
    {
        printf("-------------------------------\n");
        printf("名称\t数量\t价格\n");
        printf("%s\t%d\t", SHOP[i].name, SHOP[i].num);
        printf("\n");
    }
}
//购物车物品添加
void add_cart()
{
    while (1)
    {
        FILE* fp;
        FILE* fp1 = fopen("SHOP", "a");
        char name[10];
        printf("输入需要物品的名称：");
        scanf("%s", SHOP[cart_num].name);
        TOOL_SUFFIX(name);
        printf("\n");
        if ((fp = fopen("NAME", "r")) == NULL)
        {
            printf("文件出现错误，请检查代码部分");
            continue;
        }
        for (int j = 0; fread(NAME + j, sizeof(ZL), 1, fp) != 0; j++)
        {
            if (strcmp(NAME[j].name, SHOP[cart_num].name) == 0 && NAME[j].num != 0)
            {
                printf("已找到当前物品\n");
                printf("-----------------------------\n");
                printf("名称\t数量\t价格\n");
                printf("%s\t%d\t%d元", NAME[j].name, NAME[j].num, NAME[j].prince);
                while (1)
                {
                    printf("\n");
                    printf("输入需要的数量：");
                    scanf("%d", &SHOP[cart_num].num);
                    if (SHOP[cart_num].num > NAME[j].num)
                    {
                        printf("库存数量不足，请重新输入您需要的数量");
                        continue;
                    }
                    else
                    {
                        system("cls");
                        printf("好的，已为您记录\n\n");
                        break;
                    }
                }
                printf("还需要继续购物吗？（输入Y继续，输入N结束）");
                char  choice;
                scanf("%c%*c", &choice);
                if (choice == 'N' || choice == 'n')
                {
                    break;
                }
                else if (choice == 'Y' || choice == 'y')
                {
                    cart_num++;
                    continue;
                }
            }
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
                printf("当前无\n");
                break;
            case 2:
                add_cart();
                break;
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
                }break;
                show();
                break;
            }
            case 3:
                shop_car();
                break;
            case 4:
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
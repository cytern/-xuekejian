package com.xuexikuaile.deng.BackPojo;

/**
 * @Author: cytern
 * @Date: 2020/6/30 10:42
 */
public class chenge {

    int ex_gcd(int a,int b,int x,int y)       //扩展欧几里得 
    {
        if(b==0)
        {
            x=1;
            y=0;
            return a;
        }
        int r=ex_gcd(b,a%b,x,y);
        int t=x;
        x=y;
        y=t-a/b*y;
        return r;
    }
    int mod_reverse(int a,int b)//a*x=1(mod b) 求a的逆元x 
    {
        int d,x = 0,y = 0;
        d=ex_gcd(a,b,x,y);
        if(d==1)
            return (x%b+b)%b;
        else
            return -1;
    }

}

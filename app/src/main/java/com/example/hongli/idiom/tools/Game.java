package com.example.hongli.idiom.tools;

import android.content.Context;
import android.util.Log;

import com.example.hongli.idiom.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hongli on 2018/5/12.
 */

public class Game {
    public int m;// hangshu
    public int n;// lieshu
    public int k;// 预选词数
    int k1 = 30;
    int k2 = 20;
    public int col;// 二維數組行數
    public String[][] cross; // 用来表示二维矩阵放的字
    public int[][][] crossfrom;// 用来表示每个位置的来源 正号表示来自横的，负号表示来自纵的，对于重合的位置三维坐标第二个存第二个位置
    public String[] level;// 水平单词  存水平单词从1开始
    public String[] down;// 垂直单词
    public String[] levelmeaning;//
    public String[] downmeaning;//
    public start[] levelstart;// 横的起始坐标  levelstart[i].getheng取横成语起始地址
    public start[] downstart;// 竖的起始坐标
    public String[] readypool;// 預選詞組
    public String[] readypoolmeaning;
    private ArrayList<String> texts = new ArrayList<String>();
    // private ArrayList<String> texts = new ArrayList<String>();
    private Random rand = new Random();
    private Context context;

    public Game(int col, Context context) {
        this.context = context;
        this.col = col;
        cross = new String[col][col];
        crossfrom = new int[col][col][2];
        for (int i = 0; i < col; i++)
            for (int j = 0; j < col; j++) {
                cross[i][j] = "*";
                crossfrom[i][j][0] = 0;
                crossfrom[i][j][1] = 0;
            }

        if (col == 10) {
            k = 30;
            readypool = new String[30];
            readypoolmeaning = new String[30];
            level = new String[30];
            down = new String[30];
            levelmeaning = new String[30];
            downmeaning = new String[30];
            levelstart = new start[30];
            downstart = new start[30];

        }
        if (col == 8) {
            k = 20;
            readypool = new String[20];
            readypoolmeaning = new String[20];
            level = new String[20];
            down = new String[20];
            levelmeaning = new String[20];
            downmeaning = new String[20];
            levelstart = new start[20];
            downstart = new start[20];

        }
    }

    private void initIdoim() {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(context.getResources().openRawResource(R.raw.chengyu), "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                texts.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initMeadow() {

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(context.getResources().openRawResource(R.raw.miyu), "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String s;
        try {
            while ((s = reader.readLine()) != null) {
                int tmp1 = s.indexOf("答案：") + 3;
                int tmp2 = s.length();
                String cy = "";
                try {
                    cy = s.substring(tmp1, tmp2);
                } catch (Exception e) {
                    continue;
                }
                if (cy.length() != 4)
                    continue;
                String my = "";
                try {
                    my = s.substring(0, s.indexOf("……"));
                } catch (Exception e) {
                    continue;
                }
                texts.add(cy + "谜语：" + my);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMeaning(int i, int j) {
        if (crossfrom[i][j][1] == 0) {//不是交界
            if (crossfrom[i][j][0] > 0) {
                return levelmeaning[crossfrom[i][j][0]];
            } else if (crossfrom[i][j][0] < 0) {
                return downmeaning[-crossfrom[i][j][0]];
            }
        } else {
            StringBuilder sb = new StringBuilder();
            if (crossfrom[i][j][1] > 0) {
                sb.append(levelmeaning[crossfrom[i][j][1]]).append(";").append("\n").append(downmeaning[-crossfrom[i][j][0]]);
            } else if (crossfrom[i][j][0] < 0) {
                sb.append(levelmeaning[crossfrom[i][j][0]]).append(";").append("\n").append(downmeaning[-crossfrom[i][j][1]]);
            }
            return sb.toString();
        }
        return null;
    }


    public int[] getInfoPosition(int i, int j) {
        int[] a = new int[4];
        if ((crossfrom[i][j][0] > 0) && (crossfrom[i][j][1] == 0)) {
            int h = levelstart[crossfrom[i][j][0]].getheng();
            int s = levelstart[crossfrom[i][j][0]].getshu();
            a[0] = h;
            a[1] = s;
            a[2] = -1;
            a[3] = -1;
        }
        if ((crossfrom[i][j][0] < 0) && (crossfrom[i][j][1] == 0)) {
            int h = downstart[-crossfrom[i][j][0]].getheng();
            int s = downstart[-crossfrom[i][j][0]].getshu();
            a[0] = -1;
            a[1] = -1;
            a[2] = h;
            a[3] = s;
        }
        if ((crossfrom[i][j][0] > 0) && (crossfrom[i][j][1] < 0)) {
            int h1 = levelstart[crossfrom[i][j][0]].getheng();
            int s1 = levelstart[crossfrom[i][j][0]].getshu();
            int h2 = downstart[-crossfrom[i][j][1]].getheng();
            int s2 = downstart[-crossfrom[i][j][1]].getshu();
            a[0] = h1;
            a[1] = s1;
            a[2] = h2;
            a[3] = s2;
        }
        if ((crossfrom[i][j][0] < 0) && (crossfrom[i][j][1] > 0)) {

            int h1 = levelstart[crossfrom[i][j][1]].getheng();
            int s1 = levelstart[crossfrom[i][j][1]].getshu();
            int h2 = downstart[-crossfrom[i][j][0]].getheng();
            int s2 = downstart[-crossfrom[i][j][0]].getshu();
            a[0] = h1;
            a[1] = s1;
            a[2] = h2;
            a[3] = s2;
        }
        return a;
    }

    public void findCross() {
        //用来选出备用成语
        initIdoim();
        initMeadow();
        String wd = texts.get(rand.nextInt(texts.size()));
        String chengyu;
        String meaning;
        int sign[][] = new int[k][4];// 标记每个候选成语每个字的重复情况
        for (int i = 0; i < k; i++)
            for (int j = 0; j < 4; j++)
                sign[i][j] = 0;
        while (!this.getfirstsecond()) {

        }
        sign[0][0]++;
        sign[1][0]++;
        for (int i = 2; i < k; i++) {
            int j = 0;
            for (; j < texts.size(); j++) {
                wd = texts.get(j);
                chengyu = wd.substring(0, 4);
                meaning = wd.substring(4);
                if ((chengyu.indexOf(readypool[i - 1].charAt(2)) == 0)
                        || (chengyu.indexOf(readypool[i - 1].charAt(3)) == 0)
                        || (chengyu.indexOf(readypool[i - 1].charAt(2)) == 1)
                        || (chengyu.indexOf(readypool[i - 1].charAt(3)) == 1)) {
                    int l = 0;
                    for (; l < i; l++) {
                        if (chengyu.equalsIgnoreCase(readypool[l])) {
                            break;
                        }
                    }
                    if (l == i) {
                        readypool[i] = chengyu;
                        readypoolmeaning[i] = meaning;
                        break;
                    }
                }
            }
            if (j == texts.size()) {
                wd = texts.get(rand.nextInt(texts.size()));
                chengyu = wd.substring(0, 4);
                meaning = wd.substring(4);
                readypool[i] = chengyu;
                readypoolmeaning[i] = meaning;
            }
        }
    }

    public boolean getfirstsecond() {// 取备用池的前两个
        String wd = texts.get(rand.nextInt(texts.size()));
        String chengyu;
        String meaning;
        readypool[0] = wd.substring(0, 4);
        readypoolmeaning[0] = wd.substring(4);
        int j = 0;
        for (; j < texts.size(); j++) {
            wd = texts.get(j);
            chengyu = wd.substring(0, 4);
            meaning = wd.substring(4);
            if ((chengyu.indexOf(readypool[0].charAt(0)) == 0) && (!chengyu.equalsIgnoreCase(readypool[0]))) {
                readypool[1] = chengyu;
                readypoolmeaning[1] = meaning;
                return true;
            }
        }
        if (j == texts.size()) {
            return false;
        } else {
            return false;
        }
    }

    public void makeCross() {// 生成二位数组 down， downing,level,levelmeaning
        this.findCross();
        this.setcrossfirst();
        for (int i = 0; i < k; i++) {
            System.out.print(readypool[i]);
        }
        m = 1;
        n = 1;
        for (int i = 2; i < k; i++) {
            if (readypool[i].indexOf(readypool[i - 1].charAt(3)) == 0) {// 废物利用 这种情况
                int h, s;
                if (this.judgelevelordown(i - 1) == 1) { // 上一个是横的
                    h = levelstart[m].getheng();
                    s = levelstart[m].getshu();
                    if ((h + 3 < col)) {
                        if ((crossfrom[h + 1][s + 3][0] == 0) && (crossfrom[h + 2][s + 3][0] == 0)
                                && (crossfrom[h + 3][s + 3][0] == 0)) {
                            n++;
                            down[n] = readypool[i];
                            downmeaning[n] = readypoolmeaning[i];
                            downstart[n] = new start(h, s + 3);
                            cross[h + 1][s + 3] = String.valueOf(readypool[i].charAt(1));
                            cross[h + 2][s + 3] = String.valueOf(readypool[i].charAt(2));
                            cross[h + 3][s + 3] = String.valueOf(readypool[i].charAt(3));
                            crossfrom[h][s + 3][1] = -n;
                            crossfrom[h + 1][s + 3][0] = -n;
                            crossfrom[h + 2][s + 3][0] = -n;
                            crossfrom[h + 3][s + 3][0] = -n;
                            System.out.println("a2");
                        } else { // 不能插入，重新选择
                            break;
                        }
                    }
                } else {
                    h = downstart[n].getheng();
                    s = downstart[n].getshu();
                    if ((s + 3 < col)) {
                        if ((crossfrom[h + 3][s + 1][0] == 0) && (crossfrom[h + 3][s + 2][0] == 0)
                                && (crossfrom[h + 3][s + 3][0] == 0)) {
                            m++;
                            level[m] = readypool[i];

                            levelmeaning[m] = readypoolmeaning[i];
                            levelstart[m] = new start(h + 3, s);
                            cross[h + 3][s + 1] = String.valueOf(readypool[i].charAt(1));
                            cross[h + 3][s + 2] = String.valueOf(readypool[i].charAt(2));
                            cross[h + 3][s + 3] = String.valueOf(readypool[i].charAt(3));
                            crossfrom[h + 3][s][1] = m;
                            crossfrom[h + 3][s + 1][0] = m;
                            crossfrom[h + 3][s + 2][0] = m;
                            crossfrom[h + 3][s + 3][0] = m;
                            System.out.println("a3");
                        } else { // 不能插入，重新选择
                            break;
                        }
                    }
                }
            }
            if ((readypool[i].indexOf(readypool[i - 1].charAt(2)) == 0)
                    && (readypool[i].indexOf(readypool[i - 1].charAt(3)) != 0)) {// 废物用利 这种情况
                int h, s; // 神
                if (this.judgelevelordown(i - 1) == 1) { // 上一个是横的
                    h = levelstart[m].getheng();
                    s = levelstart[m].getshu();
                    if ((h + 3 < col)) {
                        if ((crossfrom[h + 1][s + 2][0] == 0) && (crossfrom[h + 2][s + 2][0] == 0)
                                && (crossfrom[h + 3][s + 2][0] == 0)) {
                            n++;
                            down[n] = readypool[i];
                            downmeaning[n] = readypoolmeaning[i];
                            downstart[n] = new start(h, s + 2);
                            cross[h + 1][s + 2] = String.valueOf(readypool[i].charAt(1));
                            cross[h + 2][s + 2] = String.valueOf(readypool[i].charAt(2));
                            cross[h + 3][s + 2] = String.valueOf(readypool[i].charAt(3));
                            crossfrom[h][s + 2][1] = -n;
                            crossfrom[h + 1][s + 2][0] = -n;
                            crossfrom[h + 2][s + 2][0] = -n;
                            crossfrom[h + 3][s + 2][0] = -n;
                            System.out.println("b2");
                        } else { // 不能插入，重新选择
                            //System.out.println("b");
                            break;
                        }
                    }
                } else {
                    h = downstart[n].getheng();
                    s = downstart[n].getshu();
                    if ((s + 3 < col)) {
                        if ((crossfrom[h + 2][s + 1][0] == 0) && (crossfrom[h + 2][s + 2][0] == 0)
                                && (crossfrom[h + 2][s + 3][0] == 0)) {
                            m++;
                            level[m] = readypool[i];
                            levelmeaning[m] = readypoolmeaning[i];
                            levelstart[m] = new start(h + 2, s);
                            cross[h + 2][s + 1] = String.valueOf(readypool[i].charAt(1));
                            cross[h + 2][s + 2] = String.valueOf(readypool[i].charAt(2));
                            cross[h + 2][s + 3] = String.valueOf(readypool[i].charAt(3));
                            crossfrom[h + 2][s][1] = m;
                            crossfrom[h + 2][s + 1][0] = m;
                            crossfrom[h + 2][s + 2][0] = m;
                            crossfrom[h + 2][s + 3][0] = m;
                            System.out.println("b3");
                        } else { // 不能插入，重新选择
                            //System.out.println("b1");
                            break;
                        }
                    }
                }
            }
            if (readypool[i].indexOf(readypool[i - 1].charAt(3)) == 1) {// 用 这种情况
                int h, s;
                if (this.judgelevelordown(i - 1) == 1) { // 上一个是横的
                    h = levelstart[m].getheng();
                    s = levelstart[m].getshu();
                    if ((h + 2 < col) && (h - 1 >= 0)) {
                        if ((crossfrom[h - 1][s + 3][0] == 0) && (crossfrom[h + 1][s + 3][0] == 0)
                                && (crossfrom[h + 2][s + 3][0] == 0)) {
                            n++;
                            down[n] = readypool[i];

                            downmeaning[n] = readypoolmeaning[i];
                            downstart[n] = new start(h - 1, s + 3);
                            cross[h - 1][s + 3] = String.valueOf(readypool[i].charAt(0));
                            cross[h + 1][s + 3] = String.valueOf(readypool[i].charAt(2));
                            cross[h + 2][s + 3] = String.valueOf(readypool[i].charAt(3));
                            crossfrom[h][s + 3][1] = -n;
                            crossfrom[h - 1][s + 3][0] = -n;
                            crossfrom[h + 1][s + 3][0] = -n;
                            crossfrom[h + 2][s + 3][0] = -n;
                            System.out.println("c2");
                        } else { // 不能插入，重新选择
                            break;
                        }
                    }
                } else {
                    h = downstart[n].getheng();
                    s = downstart[n].getshu();
                    if ((s + 2 < col) && (s - 1 >= 0)) {
                        if ((crossfrom[h + 3][s - 1][0] == 0) && (crossfrom[h + 3][s + 1][0] == 0)
                                && (crossfrom[h + 3][s + 2][0] == 0)) {
                            m++;
                            level[m] = readypool[i];
                            levelmeaning[m] = readypoolmeaning[i];
                            levelstart[m] = new start(h + 3, s - 1);
                            cross[h + 3][s - 1] = String.valueOf(readypool[i].charAt(0));
                            cross[h + 3][s + 1] = String.valueOf(readypool[i].charAt(2));
                            cross[h + 3][s + 2] = String.valueOf(readypool[i].charAt(3));
                            crossfrom[h + 3][s][1] = m;
                            crossfrom[h + 3][s - 1][0] = m;
                            crossfrom[h + 3][s + 1][0] = m;
                            crossfrom[h + 3][s + 2][0] = m;
                            System.out.println("c3");
                        } else { // 不能插入，重新选择
                            //System.out.println("c1");
                            break;
                        }
                    }
                }
            }
            if ((readypool[i].indexOf(readypool[i - 1].charAt(2)) == 1)
                    && (readypool[i].indexOf(readypool[i - 1].charAt(3)) != 1)) {// 用 这种情况
                int h, s; // 神
                if (this.judgelevelordown(i - 1) == 1) { // 上一个是横的
                    h = levelstart[m].getheng();
                    s = levelstart[m].getshu();
                    if ((h + 2 < col) && (h - 1 >= 0)) {
                        if ((crossfrom[h - 1][s + 2][0] == 0) && (crossfrom[h + 1][s + 2][0] == 0)
                                && (crossfrom[h + 2][s + 2][0] == 0)) {
                            n++;
                            down[n] = readypool[i];
                            downmeaning[n] = readypoolmeaning[i];
                            downstart[n] = new start(h - 1, s + 2);
                            cross[h - 1][s + 2] = String.valueOf(readypool[i].charAt(0));
                            cross[h + 1][s + 2] = String.valueOf(readypool[i].charAt(2));
                            cross[h + 2][s + 2] = String.valueOf(readypool[i].charAt(3));
                            crossfrom[h][s + 2][1] = -n;
                            crossfrom[h - 1][s + 2][0] = -n;
                            crossfrom[h + 1][s + 2][0] = -n;
                            crossfrom[h + 2][s + 2][0] = -n;
                            System.out.println("d2");
                        } else { // 不能插入，重新选择
                            //System.out.println("d");
                            break;
                        }
                    }
                } else {
                    h = downstart[n].getheng();
                    s = downstart[n].getshu();
                    if ((s + 2 < col) && (s - 1 >= 0)) {
                        if ((crossfrom[h + 2][s - 1][0] == 0) && (crossfrom[h + 2][s + 1][0] == 0)
                                && (crossfrom[h + 2][s + 2][0] == 0)) {
                            m++;
                            level[m] = readypool[i];
                            levelmeaning[m] = readypoolmeaning[i];
                            levelstart[m] = new start(h + 2, s - 1);
                            cross[h + 2][s - 1] = String.valueOf(readypool[i].charAt(0));
                            cross[h + 2][s + 1] = String.valueOf(readypool[i].charAt(2));
                            cross[h + 2][s + 2] = String.valueOf(readypool[i].charAt(3));
                            crossfrom[h + 2][s][1] = m;
                            crossfrom[h + 2][s - 1][0] = m;
                            crossfrom[h + 2][s + 1][0] = m;
                            crossfrom[h + 2][s + 2][0] = m;
                            System.out.println("d3");
                        } else { // 不能插入，重新选择
                            //System.out.println("d1");
                            break;
                        }
                    }
                }
            }
        }
        /*for (int i = 0; i < col; i++) {
            for (int j = 0; j < col; j++) {
				System.out.print(cross[i][j] + " ");
			}
			System.out.println();
		}
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(crossfrom[i][j][0] + " ");
			}
			System.out.println();
		}
		for (int i = 0; i < k; i++) {
			System.out.print(readypool[i]);
		}
		for (int i = 1; i <= n; i++) {
			System.out.println(downstart[i].getheng() + " " + downstart[i].getshu());
		}*/
    }

    public int judgelevelordown(int i) { // 判断预选池中上一个单词是横还是竖
        if (readypool[i].equalsIgnoreCase(level[m])) {
            return 1;
            // 横返回1，竖返回0
        } else {
            return 0;
        }

    }

    public void setcrossfirst() {
        //用来初始化前两个成语
        down[1] = readypool[0];
        downmeaning[1] = readypoolmeaning[0];
        level[1] = readypool[1];
        levelmeaning[1] = readypoolmeaning[1];
        if ((readypool[2].indexOf(readypool[1].charAt(3)) == 0)
                || (readypool[2].indexOf(readypool[1].charAt(2)) == 0)) {

            levelstart[1] = new start(0, 0);
            downstart[1] = new start(0, 0);

            cross[0][0] = String.valueOf(readypool[0].charAt(0));

            cross[1][0] = String.valueOf(readypool[0].charAt(1));
            cross[2][0] = String.valueOf(readypool[0].charAt(2));
            cross[3][0] = String.valueOf(readypool[0].charAt(3));
            cross[0][1] = String.valueOf(readypool[1].charAt(1));
            cross[0][2] = String.valueOf(readypool[1].charAt(2));
            cross[0][3] = String.valueOf(readypool[1].charAt(3));

            crossfrom[0][0][0] = 1;
            crossfrom[0][0][1] = -1;
            crossfrom[0][1][0] = 1;
            crossfrom[0][2][0] = 1;
            crossfrom[0][3][0] = 1;
            crossfrom[1][0][0] = -1;
            crossfrom[2][0][0] = -1;
            crossfrom[3][0][0] = -1;

        }
        if ((readypool[2].indexOf(readypool[1].charAt(3)) == 1)
                || (readypool[2].indexOf(readypool[1].charAt(2)) == 1)) {

            levelstart[1] = new start(1, 0);
            downstart[1] = new start(1, 0);

            cross[1][0] = String.valueOf(readypool[0].charAt(0));

            cross[2][0] = String.valueOf(readypool[0].charAt(1));
            cross[3][0] = String.valueOf(readypool[0].charAt(2));
            cross[4][0] = String.valueOf(readypool[0].charAt(3));
            cross[1][1] = String.valueOf(readypool[1].charAt(1));
            cross[1][2] = String.valueOf(readypool[1].charAt(2));
            cross[1][3] = String.valueOf(readypool[1].charAt(3));

            crossfrom[1][0][0] = 1;
            crossfrom[1][0][1] = -1;
            crossfrom[1][1][0] = 1;
            crossfrom[1][2][0] = 1;
            crossfrom[1][3][0] = 1;
            crossfrom[2][0][0] = -1;
            crossfrom[3][0][0] = -1;
            crossfrom[4][0][0] = -1;

        }
    }

    class start {
        //用来存纵横字谜的起始坐标
        private int heng;
        private int shu;

        public start() {

        }

        public start(int heng, int shu) {
            this.heng = heng;
            this.shu = shu;

        }

        public void setheng(int heng) {
            this.heng = heng;
        }

        public void setshu(int shu) {
            this.shu = shu;
        }

        public int getheng() {
            return heng;
        }

        public int getshu() {
            return shu;
        }

    }
}
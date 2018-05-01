package com.example.cocotte.sudoku;

import android.content.Context;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Cocotte on 25/04/2018.
 */
class Line {

    int startX, startY, stopX, stopY;
    Paint paint;
    public Line(int startX, int startY, int stopX, int stopY, int weight){
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        paint = new Paint();
        paint.setColor(Color.rgb(255,255,255));
        paint.setStrokeWidth(weight);
    }
    public void draw(Canvas canvas){
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
}

class Button {

    int posX, posY, radius, value;
    Paint paintCircle, paintText;
    public Button(int posX, int posY, int radius, int textSize, int value){
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.value = value;
        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setColor(Color.rgb(255,255,255));
        paintCircle.setStrokeWidth(6);
        paintCircle.setAlpha(255);
        paintCircle.setAntiAlias(true);
        paintText = new Paint();
        paintText.setColor(Color.rgb(255,255,255));
        paintText.setTextSize(textSize);
    }
    public void draw(Canvas canvas){
        canvas.drawCircle(posX, posY, radius, paintCircle);
        canvas.drawText(""+value, posX-30, posY+30, paintText);
    }
}

class Number {
    int posX, posY;
    String value;
    Paint paintText;
    public Number(int posX, int posY, int textSize, String value, int color){
        this.posX = posX;
        this.posY = posY;
        this.value = value;
        paintText = new Paint();
        paintText.setColor(color);
        paintText.setTextSize(textSize);
    }
    public void draw(Canvas canvas){
        canvas.drawText(""+value, posX-30, posY+30, paintText);
    }
}

public class Board extends View implements View.OnTouchListener{

    boolean isDragged;
    int selectedButton;
    int selectedX, selectedY;
    LinkedList<Line> lines;
    LinkedList<Button> buttons;
    LinkedList<Number> numbers;
    List<List<Integer>> grid;
    List<List<Boolean>> gridModification;
    List<List<Boolean>> gridValid;
    int maxWidth, maxHeight, unitWidth, unitHeight, padding, marginTop, buttonStep;

    public Board(Context context, AttributeSet attributes) {
        super(context, attributes);
        isDragged = false;
        grid = new ArrayList<List<Integer>>();
        gridModification = new ArrayList<List<Boolean>>();
        gridValid = new ArrayList<List<Boolean>>();
        padding = 120;
        marginTop = 300;
        maxWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        maxHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        lines = new LinkedList<Line>();
        buttons = new LinkedList<Button>();
        numbers = new LinkedList<Number>();

        unitWidth = maxWidth / 3  ;
        unitHeight = unitWidth;
        for(int i = 0; i <= 3; i ++){
            lines.push(new Line(unitWidth*0, marginTop+unitHeight*i, unitWidth*3, marginTop+unitHeight*i, 7));
            lines.push(new Line(unitWidth*i, marginTop+unitHeight*0, unitWidth*i, marginTop+unitHeight*3, 7));
        }
        unitWidth = maxWidth / 9 ;
        unitHeight = unitWidth;
        for(int i = 0; i <= 9; i ++){
            lines.push(new Line(unitWidth*0, marginTop+unitHeight*i, unitWidth*9, marginTop+unitHeight*i, 2));
            lines.push(new Line(unitWidth*i, marginTop+unitHeight*0, unitWidth*i, marginTop+unitHeight*9, 2));
        }
        buttonStep = maxWidth / 12 ;
        buttons.push(new Button(buttonStep*2,maxHeight-450,90, 100, 1));
        buttons.push(new Button(buttonStep*4,maxHeight-450,90, 100, 2));
        buttons.push(new Button(buttonStep*6,maxHeight-450,90, 100, 3));
        buttons.push(new Button(buttonStep*8,maxHeight-450,90, 100, 4));
        buttons.push(new Button(buttonStep*10,maxHeight-450,90, 100, 5));
        buttons.push(new Button(buttonStep*3,maxHeight-250,90, 100, 6));
        buttons.push(new Button(buttonStep*5,maxHeight-250,90, 100, 7));
        buttons.push(new Button(buttonStep*7,maxHeight-250,90, 100, 8));
        buttons.push(new Button(buttonStep*9,maxHeight-250,90, 100, 9));
        this.setOnTouchListener(this);
    }

    public void setGrid(String board){
        for(int i = 0; i<9; i++){
            String [] strArray = board.substring(i*9+1, i*9+9).split("");
            Integer[] boardValues = new Integer[ strArray.length ];
            int j = 0;
            for ( String textValue : strArray ) {
                if(textValue.equals("")) {
                    boardValues[j] = 0;
                }else{
                    boardValues[j] = Integer.parseInt( textValue );
                }
                j++;
            }
            grid.add(Arrays.asList(boardValues));
        }
        for (int i = 0; i < 9; i++){
            Boolean[] tempGridConfirmation = new Boolean[9];
            for(int j = 0; j < 9; j++){
                if(grid.get(j).get(i) == 0){
                    tempGridConfirmation[j] = true;
                }else{
                    tempGridConfirmation[j] = false;
                }
            }
            gridModification.add(Arrays.asList(tempGridConfirmation));
        }
        for (int i = 0; i < 9; i++){
            gridValid.add(Arrays.asList(false, false, false, false, false, false, false, false, false));
        }
    }

    boolean checkWin(){
        for(int i = 0; i < 9; i ++){
            for(int j = 0; j < 9; j ++) {
                if(!gridValid.get(j).get(i)){
                    return false;
                }
            }
        }
        for(int i = 0; i < 9; i ++){
            for(int j = 0; j < 9; j ++) {
                if(grid.get(j).get(i)==0){
                    return false;
                }
            }
        }
        return true;
    }

    void checkValidMove(int currentX, int currentY, int value){
        boolean valid = true;
        for(int x = 0; x < 9; x++){
            if(grid.get(currentY).get(x) == value && x!=currentX){
                valid = false;
            }
        }
        for(int y = 0; y < 9; y++){
            if(grid.get(y).get(currentX) == value && y!=currentY){
                valid = false;
            }
        }
        int cubeX = currentX/3 * 3;
        int cubeY = currentY/3 * 3;
        for(int y = cubeY; y < cubeY+3; y++){
            for(int x = cubeX; x < cubeX+3; x++){
                if(grid.get(y).get(x) == value && y!=currentY && x!=currentX){
                    valid = false;
                }
            }
        }
        gridValid.get(currentY).set(currentX, valid);
    }

    public void onDraw(Canvas canvas){

        if(!isDragged){
            for(int i = 0; i < 9; i ++){
                for(int j = 0; j < 9; j ++) {
                    checkValidMove(i, j, grid.get(j).get(i));
                }
            }
            for(int i = 0; i < 9; i ++){
                for(int j = 0; j < 9; j ++) {
                    String label =(grid.get(j).get(i)!=0) ? ""+grid.get(j).get(i) : " ";
                    int color;
                    if(!gridModification.get(i).get(j)){
                        color = Color.rgb(255,255,255);
                    }else{
                        if(gridValid.get(j).get(i)){
                            color =  Color.rgb(41,128,185);
                        }else{
                            color =  Color.rgb(192,57,43);
                        }
                    }
                    numbers.push(new Number(85+unitWidth*i, marginTop+80+unitHeight*j, 100, label , color));
                }
            }
        }
        for (Line line : lines){
            line.draw(canvas);
        }
        for (Button button : buttons){
            button.draw(canvas);
        }
        for (Number number : numbers){
            number.draw(canvas);
        }
        if(selectedButton!=0 && isDragged){
            new Number(selectedX, selectedY , 100, ""+selectedButton, Color.rgb(255,255,255)).draw(canvas);
        }
        Log.d("win", ""+checkWin());
    }

    public int getSelectedButton(int x, int y){
        int step = maxWidth / 12;
        int value = 0;
        for(int i= 1; i<11; i++){
            if(x>step*i-90 && x<step*i+90) {
                if (y > maxHeight - 450 - 90 && y < maxHeight - 450 + 90) {
                    if(!(i/2 < 1 || i/2 > 5) && (i%2 == 0)) {
                        value = i/2;
                    }
                }
                if (y > maxHeight - 250 - 90 && y < maxHeight - 250 + 90) {
                    if(!(i/2+5 < 6 || i/2+5 > 9) && !(i%2 == 0)) {
                        value = i/2+5;
                    }
                }
            }
        }
        return value;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        selectedX = (int)motionEvent.getX();
        selectedY = (int)motionEvent.getY();
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                isDragged = true;
                selectedButton = getSelectedButton(selectedX,selectedY);
                break;
            case MotionEvent.ACTION_UP:
                if(selectedButton == 0) return false;
                int i = (selectedX /(maxWidth/9));
                int j = (selectedY /(maxWidth/9)) - 2;
                if(!(i>8 || i<0) && !(j>8 || j<0)){
                    if(gridModification.get(i).get(j)){
                        grid.get(j).set(i, selectedButton);
                    }
                }
                isDragged = false;
                selectedButton = 0;
                break;
        }
        this.invalidate();
        return true;
    }
}

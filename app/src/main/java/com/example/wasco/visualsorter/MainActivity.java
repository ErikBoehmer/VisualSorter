package com.example.wasco.visualsorter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private ListView sortedLV, unsortedLV;
    private ArrayAdapter<String> sortedAA, unsortedAA;
    private int[] sortedNumbers, unsortedNumbers;
    private String[] sortedStrings, unsortedStrings;
    private final int numberOfElements = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sortedLV = this.findViewById(R.id.sortedLV);
        this.unsortedLV = this.findViewById(R.id.unsortedLV);

        this.sortedNumbers = new int[this.numberOfElements];
        this.unsortedNumbers = new int[this.numberOfElements];
        this.sortedStrings = new String[this.numberOfElements];
        this.unsortedStrings = new String[this.numberOfElements];

        this.sortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.sortedStrings);
        this.unsortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.unsortedStrings);

        this.sortedLV.setAdapter(this.sortedAA);
        this.unsortedLV.setAdapter(this.unsortedAA);

        this.initializeArrays();
    }

    private void insertionSort(int[] ar)
    {
        int theFollower, swap;

        for(int currStart = 1; currStart < ar.length; currStart++)
        {
            theFollower = currStart;
            while(theFollower > 0 && ar[theFollower] < ar[theFollower-1])
            {
                swap = ar[theFollower];
                ar[theFollower] = ar[theFollower-1];
                ar[theFollower-1] = swap;
                theFollower--;
            }
        }
    }

    private void mergeHelper(int[] leftarray, int[] rightarray, int[] startingarrayvalue)
    {
        int begin1 = 0;
        int begin2 = 0;

        for(int i = 0; i < startingarrayvalue.length; i++)
        {
            if(begin1 >= leftarray.length)
            {
                startingarrayvalue[i] = rightarray[begin2];
                begin2++;
            }
            else if(begin2 >= rightarray.length)
            {
                startingarrayvalue[i] = leftarray[begin1];
                begin1++;
            }
            else if(rightarray[begin2]>leftarray[begin1])
            {
                startingarrayvalue[i] = leftarray[begin1];
                begin1++;
            }
            else
            {
                startingarrayvalue[i] = rightarray[begin2];
                begin2++;
            }

        }

    }

    private void mergeSort(int[] ar)
    {
        if(ar.length != 1)
        {
            int[] leftarray = new int[ar.length / 2];
            int[] rightarray = new int[ar.length - leftarray.length];
            for(int i = 0; i<ar.length; i++)
            {
                if(i<ar.length/2)
                {
                    leftarray[i] = ar[i];
                }
                else
                {
                    rightarray[i - ar.length /2] = ar[i];
                }
            }
            mergeSort(leftarray);
            mergeSort(rightarray);
            mergeHelper(leftarray,rightarray,ar);

        }


    }

    public void insertionSortButtonPressed(View vy)
    {
        //perform an insertion sort on the unsortedArray
        this.insertionSort(this.unsortedNumbers);
        this.updateStringArrays();
    }

    public void mergeSortButtonPressed(View vy)
    {
        //perform a merge sort on the unsortedArray
        this.mergeSort(this.unsortedNumbers);
        this.updateStringArrays();
    }

    public void resetButtonPressed(View austin)
    {
        this.initializeArrays();
    }

    private void initializeArrays()
    {
        //fill my unsorted int array
        this.fillRandomIntArray(this.unsortedNumbers);
        this.copyContentsOfIntArrays(this.unsortedNumbers, this.sortedNumbers);
        this.updateStringArrays();
    }

    private void updateStringArrays()
    {
        //fill my string arrays to mimic the int arrays
        this.copyIntArrayToStringArray(this.unsortedNumbers, this.unsortedStrings);
        this.copyIntArrayToStringArray(this.sortedNumbers, this.sortedStrings);
        this.sortedAA.notifyDataSetChanged();
        this.unsortedAA.notifyDataSetChanged();
    }

    private void copyIntArrayToStringArray(int[] arInts, String[] arStrings)
    {
        for(int i = 0; i < arInts.length; i++)
        {
            arStrings[i] = "" + arInts[i];

        }
    }

    private void copyContentsOfIntArrays(int[] source, int[] destination)
    {
        for(int i = 0; i < source.length; i++)
        {
            destination[i] = source[i];
        }
    }

    private void fillRandomIntArray(int[] ar)
    {
        Random r = new Random();

        for(int i = 0; i < ar.length; i++)
        {
            ar[i] = r.nextInt(500);
        }
    }
}

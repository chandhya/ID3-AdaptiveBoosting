package adaBoosting;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Main {
	String inputFile="input.txt";
	static int iCount;
	int noOfValues;
	double epsilon;
	double[] x;
	int[] y; 
	double[] prob;
	double[] newProb;
	double newPi[];
	static double classifierValue;
	double errorNow = 0.0;
	double errorNew =20.0;
	int mistakes;
	static int errorCount[];
	static int[] errorArray;
	double alphat;
	double qiValue;
	String fnValue[];
	static String flag;
	static String type;
	public static void main(String args[])
	{
		Main m = new Main();
		//type = "realAda";
		m.readFile();
		classifierValue = m.pickClassifier();
		if(type!=null &&type.equals("realAda"))
			classifierValue = m.pickClassifier();
		m.pickClassifierForRealAda();
		//pickClassifierOnError();
		m.computeValues(errorCount);
		for(int a=0;a<iCount;a++)
		{
			flag ="recompute";
			
			classifierValue = m.pickClassifier();
			m.computeValues(errorArray);
		}
		if(type!=null &&type.equals("realAda"))
		{
			flag ="recompute";
			
			classifierValue = m.pickClassifierForRealAda();
			m.computeValues(errorArray);
		}
	}

	
private double pickClassifierForRealAda() {
	double classifier=0.0;
	int seperator =1;
	int error=0;
	double Gvalue = noOfValues;
	for(int l=0;l<noOfValues;l++)
	{
		
		int leftpostiveCount =0;
		int leftnegativeCount =0;
		int rightPositiveCount =0;
		int rightNegativeCount =0;
		double positivePr =0.0;
		double negativePr =0.0;
		double positivePw = 0.0;
		double negativePw =0.0;
		
		int[] errorLeftCount = new int[noOfValues];
		int[] errorRightCount =  new int[noOfValues];
		errorArray = new int[noOfValues];
		
		
		for(int m=0;m<seperator;m++)
		{
			if(y[m] == 1)
			{
			leftpostiveCount++;
			errorRightCount[l] =m; 
			}
			else if(y[m]== -1)
			{
			leftnegativeCount++;
			errorLeftCount[l]=m;
				
			}
		
		}

		for(int h=seperator;h<noOfValues;h++)
		{
			if(y[h] == 1)
			{
			rightPositiveCount++;
			errorLeftCount[l]=h;
			}
			else if(y[h]== -1)
			{
			rightNegativeCount++;
			errorRightCount[l] =h;
			}
		}
	positivePr = leftpostiveCount*prob[l]+rightPositiveCount*prob[l];
	negativePr = rightNegativeCount*prob[l]+leftnegativeCount*prob[l];
	positivePw=rightNegativeCount*prob[l]+leftpostiveCount*prob[l];
	negativePw=leftnegativeCount*prob[l]+rightPositiveCount*prob[l];
	double newGvalue =Math.sqrt(negativePw*positivePw)+Math.sqrt(positivePr*negativePr);
	if(newGvalue<Gvalue)
	{
		classifier = x[seperator-1]+0.5;
	}
	}
	return classifier;
		
	}


	//Selection of classifier
	private double pickClassifier() {
		
		double classifier=0.0;
		int seperator =1;
		int error=0;
		for(int l=0;l<noOfValues;l++)
		{
			
			int leftpostiveCount =0;
			int leftnegativeCount =0;
			int rightPositiveCount =0;
			int rightNegativeCount =0;
			
			int errorLeft;
			int errorRight;
			int[] errorLeftCount = new int[noOfValues];
			int[] errorRightCount =  new int[noOfValues];
			errorArray = new int[noOfValues];
			
			
			for(int m=0;m<seperator;m++)
			{
				if(y[m] == 1)
				{
				leftpostiveCount++;
				errorRightCount[l] =m; 
				}
				else if(y[m]== -1)
				{
				leftnegativeCount++;
				errorLeftCount[l]=m;
					
				}
			
			}

			for(int h=seperator;h<noOfValues;h++)
			{
				if(y[h] == 1)
				{
				rightPositiveCount++;
				errorLeftCount[l]=h;
				}
				else if(y[h]== -1)
				{
				rightNegativeCount++;
				errorRightCount[l] =h;
				}
			}
			errorLeft = leftnegativeCount+rightPositiveCount;
			errorRight = leftpostiveCount+rightNegativeCount;
			if(errorLeft<errorRight)
			{
				error = errorLeft;
				
			}
			else
			{
				error = errorRight;
				
			}
			if(flag!=null && flag.equals("recompute")&&seperator<noOfValues)
			{
				
				
				errorNew =computeError(newPi);
				errorCount = new int[noOfValues];
				if(errorLeft<errorRight)
				{
					for(int e=0;e<seperator;e++)
					{
					if(y[e]==-1)
						errorCount[e]= e;
					}
					
				for(int e=seperator;e<noOfValues;e++)
				{
				if(y[e]==1)
					errorCount[e]= e;
				}
				
				
				}
				else
				{
				
					
				for(int e=0;e<seperator;e++)
				{
				if(y[e]==1)
					errorCount[e]=e;
				}
				
			for(int e=seperator;e<noOfValues;e++)
			{
			if(y[e]==-1)
				errorCount[e]= e;
			}
				
				}
				mistakes = error;
				
				if(computeError(newPi)>errorNew)
				{
					
				classifier = x[seperator-1]+0.5;
				errorArray = errorCount;
				}
			}
		
			if(flag== null || !flag.equals("recompute"))
			{
				if(error<mistakes)
			{
				errorCount = new int[error];
				if(errorLeft<errorRight)
				{
					
				for(int e=0;e<seperator;e++)
					{
					if(y[e]==-1)
						errorCount[e]= e;
					}
					
				for(int e=seperator;e<noOfValues;e++)
				{
				if(y[e]==1)
					errorCount[e]= e;
				}
				
				
				}
				else
				{
				for(int e=0;e<seperator;e++)
				{
				if(y[e]==1)
					errorCount[e]=e;
				}
				
			for(int e=seperator;e<noOfValues;e++)
			{
			if(y[e]==-1)
				errorCount[e]= e;
			}
				
				}
				mistakes = error;
				classifier = x[seperator-1]+0.5;
			}
			}
			seperator++;	
			
		}
		errorNow =0.0;
		computeError(prob);
		System.out.println("Number of mistakes is - "+mistakes);
		
		System.out.println("Classifier Selected now is - "+classifier);
		
		
		return classifier;
	}
//Calculation of values
	private void computeValues(int[] errorCount) 
	{
		double weightedError = 0.0;
		double probabilitySum =0.0;
		double Bound=1.0;
		double qVlaue[]=new double[noOfValues];
		double preNorm[]=new double[noOfValues];
		double ZValue=0.0;
		newPi = new double[noOfValues];
			for(int s=0;s<errorCount.length;s++)
			{
				probabilitySum = probabilitySum + prob[errorCount[s]];
			}
			System.out.println("Error of ht -"+probabilitySum);
			weightedError = probabilitySum;
			
			alphat = 0.5* (Math.log10((1-weightedError)/weightedError)/Math.log((1-weightedError)/weightedError));
			System.out.println("alphat"+alphat);
			for(int u=0;u<noOfValues;u++)
			{
				for(int s=0;s<errorCount.length;s++)
				{if(u!=errorCount[s])
				{
				qVlaue[u] = Math.exp((-1)*alphat);
				preNorm[u]=qVlaue[u] *prob[u];
				}
				else
				{
				qVlaue[u] = Math.exp(alphat);
				preNorm[u]=qVlaue[u] *prob[u];
				}
				}
				 
			}
			
			for(int y=0;y<noOfValues;y++)
			{
				ZValue = ZValue+preNorm[y];
			}
			System.out.println("Probabilites normalization factor ZValue"+ZValue);
			for(int w=0;w<noOfValues;w++)
			{
				newPi[w]=preNorm[w]/ZValue;
				System.out.println("Probabilites after normalization"+newPi[w]);
			}
			
			errorNow =0.0;
			computeError(newPi);
			System.out.println("Error on boosted classifier"+errorNow);
			Bound = Bound*ZValue;
			System.out.println("Bound value"+Bound);
	}

	private double computeError(double[] newPi) {
		for(int o=0;o<noOfValues;o++)
		{
			for(int s=0;s<errorCount.length;s++)
			{
				if(o==errorCount[s])
				{
					errorNow = errorNow+newPi[o];
				}
			}
		}
		return errorNow;
	}
//Reading the input file
	private void readFile() {
		Scanner sn;
		try {
			sn = new Scanner(new File(inputFile));
		
		iCount = sn.nextInt();
		noOfValues = sn.nextInt();
		epsilon = sn.nextDouble();
		x = new double[noOfValues];
		y = new int[noOfValues];
		prob = new double[noOfValues];
		for(int i=0;i<noOfValues;i++)
		{
			x[i]= sn.nextDouble();
		}
		for(int j=0;j<noOfValues;j++)
		{
			y[j] =  sn.nextInt();
			
		}
		for(int k=0;k<noOfValues;k++)
		{
			prob[k] =  sn.nextDouble();
			
		}
		fnValue = new String[iCount];
		mistakes = noOfValues;
	} 
		
		catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}

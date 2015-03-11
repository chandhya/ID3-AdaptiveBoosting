import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;




public class Main extends Entry{
	
	//public static String inputFile = "test.txt";
	//public static String partitionFile = "partition.txt";
	public static String inputFile = "test.txt";
	public static String partitionFile = "partition.txt";

	public static double num_Of_Instances;
	
	public static String strLine;
	public static int featureCount;
	public static List<Entry> instanceList;
	public static int instanceId;
	public static List<Partition> partitionList;
	public static List<String> lines;
	
	
	/*
	public static double[] zeroCount = new double[3];
	public static double[] oneCount =new double[3];
	public static double[] twoCount =new double[3];
	public static int targetZeroCount =0;
	public static int targetOneCount =0;
	public static double[] ZeroForTargetZero =new double[3];
	public static double[] ZeroForTargetOne =new double[3];
	public static double[] OneForTargetZero =new double[3];
	public static double[] OneForTargetOne =new double[3];
	public static double[] TwoForTargetZero =new double[3];
	public static double[] TwoForTargetOne =new double[3];
	*/
	Scanner sn;
	public static void main(String args[])
	{
		Main main = new Main();
		System.out.println("Enter names of files :");
		Scanner inp = new Scanner(System.in);
		inputFile  = inp.nextLine();
		partitionFile = inp.nextLine();
		main.readFile();
		main.computeEntropy();
		
	}

	private void computeEntropy() {
		double F[] = new double[partitionList.size()];
		double FCopy[] = new double[partitionList.size()];
		double[][] gainArray = new double[featureCount][partitionList.size()];
		double[][] gainArrayCopy = new double[featureCount][partitionList.size()];
		
		for(int k=0;k<partitionList.size();k++)
		{
			double[] entropyCn = new double[featureCount];
			double entropy = 0.0;
			//gaina0 =  new double[3];
			//gaina1=  new double[3];
			//gaina2=  new double[3];
			double[] zeroCount = new double[featureCount];
			double[] oneCount =new double[featureCount];
			double[] twoCount =new double[featureCount];
			int targetZeroCount =0;
			int targetOneCount =0;
			double[] ZeroForTargetZero =new double[featureCount];
			double[] ZeroForTargetOne =new double[featureCount];
			double[] OneForTargetZero =new double[featureCount];
			double[] OneForTargetOne =new double[featureCount];
			double[] TwoForTargetZero =new double[featureCount];
			double[] TwoForTargetOne =new double[featureCount];
			double section1 = 0;
			double section2 =0;
			double partitionSize = partitionList.get(k).values.size();
			
			for(int l=0;l<partitionList.get(k).values.size();l++)
			{
				//.out.println("got here!");
				int instance = partitionList.get(k).values.get(l);
				//System.out.println("Instance"+instance);
				instanceId =0;
				for(int j=0;j<instanceList.size();j++)
				{
					instanceId++;
					//System.out.println("instanceId - "+instanceId);
				if(instanceId==instance)
				{
					//System.out.println("Instance Now"+instanceId);
					if(instanceList.get(j).targetAtr == 0)
					{
					targetZeroCount++;
					//System.out.println("targetZeroCount -" +targetZeroCount+ "for Instance"+instanceId);
					}
					else if(instanceList.get(j).targetAtr == 1)
					{
					targetOneCount++;
					//System.out.println(targetOneCount);
					}
					
					for(int r=0;r<instanceList.get(j).features.size();r++)
					{
						if(instanceList.get(j).features.get(r).featureValue == 0)
						{
							zeroCount[r]++;
							//System.out.println(zeroCount[0]);
							if(instanceList.get(j).targetAtr == 0 )
							{
								ZeroForTargetZero[r]++;
								//System.out.println(ZeroForTargetZero[0]);
							}
							else if(instanceList.get(j).targetAtr == 1)
							{
								ZeroForTargetOne[r]++;
								//System.out.println(ZeroForTargetOne[0]);
							}
								
						}
						if(instanceList.get(j).features.get(r).featureValue == 1)
						{
							oneCount[r]++;
							if(instanceList.get(j).targetAtr == 0 )
							{
								OneForTargetZero[r]++;
								//System.out.println(OneForTargetZero[0]);
							}
							else if(instanceList.get(j).targetAtr == 1)
							{
								OneForTargetOne[r]++;
								//System.out.println(OneForTargetOne[0]);
							}
						}
						if(instanceList.get(j).features.get(r).featureValue == 2)
						{
							oneCount[r]++;
							if(instanceList.get(j).targetAtr == 0 )
							{
								TwoForTargetZero[r]++;
								//System.out.println(OneForTargetZero[0]);
							}
							else if(instanceList.get(j).targetAtr == 1)
							{
								TwoForTargetOne[r]++;
								//System.out.println(OneForTargetOne[0]);
							}
						}
					}
				/*	if(instanceList.get(j).a0 == 0)
					{
						zeroCount[0]++;
						//System.out.println(zeroCount[0]);
						if(instanceList.get(j).targetAtr == 0 )
						{
							ZeroForTargetZero[0]++;
							//System.out.println(ZeroForTargetZero[0]);
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							ZeroForTargetOne[0]++;
							//System.out.println(ZeroForTargetOne[0]);
						}
							
					}
					if(instanceList.get(j).a0 == 1)
					{
						oneCount[0]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							OneForTargetZero[0]++;
							//System.out.println(OneForTargetZero[0]);
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							OneForTargetOne[0]++;
							//System.out.println(OneForTargetOne[0]);
						}
					}
					if(instanceList.get(j).a0 == 2)
					{
						twoCount[0]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							TwoForTargetZero[0]++;
							//System.out.println(TwoForTargetZero[0]);
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							TwoForTargetOne[0]++;
							//System.out.println(TwoForTargetOne[0]);
						}
					}
					
					if(instanceList.get(j).a1 == 0)
					{
						zeroCount[1]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							ZeroForTargetZero[1]++;
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							ZeroForTargetOne[1]++;
						}
							
					}
					if(instanceList.get(j).a1 == 1)
					{
						oneCount[1]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							OneForTargetZero[1]++;
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							OneForTargetOne[1]++;
						}
					}
					if(instanceList.get(j).a1 == 2)
					{
						twoCount[1]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							TwoForTargetZero[1]++;
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							TwoForTargetOne[1]++;
						}
					}
					if(instanceList.get(j).a2 == 0)
					{
						zeroCount[2]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							ZeroForTargetZero[2]++;
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							ZeroForTargetOne[2]++;
						}
							
					}
					if(instanceList.get(j).a2 == 1)
					{
						oneCount[2]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							OneForTargetZero[2]++;
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							OneForTargetOne[2]++;
						}
					}
					if(instanceList.get(j).a2 == 2)
					{
						twoCount[2]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							TwoForTargetZero[2]++;
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							TwoForTargetOne[2]++;
						}
					}
					if(instanceList.get(j).a3 == 0)
					{
						zeroCount[3]++;
						//System.out.println(zeroCount[0]);
						if(instanceList.get(j).targetAtr == 0 )
						{
							ZeroForTargetZero[3]++;
							//System.out.println(ZeroForTargetZero[0]);
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							ZeroForTargetOne[3]++;
							//System.out.println(ZeroForTargetOne[0]);
						}
							
					}
					if(instanceList.get(j).a3 == 1)
					{
						oneCount[3]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							OneForTargetZero[3]++;
							//System.out.println(OneForTargetZero[0]);
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							OneForTargetOne[3]++;
							//System.out.println(OneForTargetOne[0]);
						}
					}
					if(instanceList.get(j).a3 == 2)
					{
						twoCount[3]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							TwoForTargetZero[3]++;
							//System.out.println(TwoForTargetZero[0]);
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							TwoForTargetOne[3]++;
							//System.out.println(TwoForTargetOne[0]);
						}
					}
					if(instanceList.get(j).a4 == 0)
					{
						zeroCount[4]++;
						//System.out.println(zeroCount[0]);
						if(instanceList.get(j).targetAtr == 0 )
						{
							ZeroForTargetZero[4]++;
							//System.out.println(ZeroForTargetZero[0]);
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							ZeroForTargetOne[4]++;
							//System.out.println(ZeroForTargetOne[0]);
						}
							
					}
					if(instanceList.get(j).a4 == 1)
					{
						oneCount[4]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							OneForTargetZero[4]++;
							//System.out.println(OneForTargetZero[0]);
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							OneForTargetOne[4]++;
							//System.out.println(OneForTargetOne[0]);
						}
					}
					if(instanceList.get(j).a4 == 2)
					{
						twoCount[4]++;
						if(instanceList.get(j).targetAtr == 0 )
						{
							TwoForTargetZero[4]++;
							//System.out.println(TwoForTargetZero[0]);
						}
						else if(instanceList.get(j).targetAtr == 1)
						{
							TwoForTargetOne[4]++;
							//System.out.println(TwoForTargetOne[0]);
						}
					}
					
					
				}
					
				}
				
			}*/
			/*
			System.out.println("targetZeroCount - "+targetZeroCount);
			System.out.println("ZeroForTargetZero - "+ZeroForTargetZero[0]);
			System.out.println("targetOneCount - "+targetOneCount);
			System.out.println("TwoForTargetZero - "+TwoForTargetZero[0]);*/
			//System.out.println(TwoForTargetOne[0]);
			double term1section1=0.0;
			double term1section2=0.0;
			double term2section1 =0.0;
			double term2section2 = 0.0;
			double term3section1 =0.0;
			double term3section2 =0.0;
			for(int y=0;y<3;y++)
			{
				//System.out.println(partitionSize);
				/*System.out.println(targetZeroCount);
				System.out.println(ZeroForTargetZero[y]);
				System.out.println(targetOneCount);
				System.out.println(ZeroForTargetOne[y]);
				*/
				//For term1
				if(targetZeroCount == 0 || ZeroForTargetZero[y] ==0)
					term1section1=0.0;
				else if(targetZeroCount > 0)
				{
					term1section1 =(ZeroForTargetZero[0]/targetZeroCount)*log2(targetZeroCount/ZeroForTargetZero[0]);
					
					//System.out.println(term1section1);
					
				}
				if(targetOneCount == 0 ||ZeroForTargetOne[y] ==0 )
				{		term1section2 =0.0;
				//System.out.println("Section2"+term1section2);
				}
				else if(targetOneCount >0)
				{
					term1section2 = (ZeroForTargetOne[y]/targetOneCount)*log2(targetOneCount/ZeroForTargetOne[y]);
					//System.out.println("Section2"+term1section2);
				}
				//For term2
				if(targetZeroCount == 0 || OneForTargetZero[y] ==0)
					term2section1=0.0;
				else if(targetZeroCount > 0)
				{
					term2section1 =(OneForTargetZero[y]/targetZeroCount)*log2(targetZeroCount/OneForTargetZero[y]);
					
					//System.out.println(term2section1);
					
				}
				if(targetZeroCount == 0 || OneForTargetOne[y] ==0)
					term2section2=0.0;
				else if(targetZeroCount > 0)
				{
					term2section2 =(OneForTargetOne[y]/targetZeroCount)*log2(targetZeroCount/OneForTargetOne[y]);
					
					//System.out.println(term2section1);
					
				}
				//For term3
				if(targetZeroCount == 0 || TwoForTargetZero[y] ==0)
					term3section1=0.0;
				else if(targetZeroCount > 0)
				{
					term3section1 =(TwoForTargetZero[y]/targetZeroCount)*log2(targetZeroCount/TwoForTargetZero[y]);
					
					//System.out.println(term2section1);
					
				}
				if(targetZeroCount == 0 || TwoForTargetOne[y] ==0)
					term3section2=0.0;
				else if(targetZeroCount > 0)
				{
					term3section2 =(TwoForTargetZero[y]/targetZeroCount)*log2(targetZeroCount/TwoForTargetOne[y]);
					
					//System.out.println(term2section1);
					
				}
			double term1 = ((zeroCount[y]/partitionSize)*(term1section1)+(term1section2));
			double term2 = ((oneCount[y]/partitionSize)*(term2section1)+(term2section2));
			double term3 = ((twoCount[y]/partitionSize)*(term3section1)+(term3section2));
		//	System.out.println("term1 - " +term1);
			//System.out.println("term2 - "+term2);
			//System.out.println("term3 - "+term3);
			//double term2 = ((oneCount[y]/partitionSize)*((OneForTargetZero[y]/targetZeroCount)*Math.log(targetZeroCount/OneForTargetZero[y]))+((OneForTargetOne[y]/targetOneCount)*Math.log(targetOneCount/OneForTargetOne[y])));	
			//double term3 = ((twoCount[y]/partitionSize)*((TwoForTargetZero[y]/targetZeroCount)*Math.log(targetZeroCount/TwoForTargetZero[y]))+((TwoForTargetOne[y]/targetOneCount)*Math.log(targetOneCount/TwoForTargetOne[y])));
			entropyCn[y] = term1 + term2 + term3;
			//entropyCn[y] = term1;
			}
			if(targetZeroCount == 0)
				{section1 =0.0;
				System.out.println("tcount"+targetZeroCount);
				}
			else if(targetZeroCount > 0)
				{
				//System.out.println("part size"+partitionSize);
				section1= (targetZeroCount/ partitionSize)*log2(partitionSize/targetZeroCount);
			//System.out.println("section1"+section1);
				}
			if(targetOneCount == 0)
				{section2 = 0.0;
			//System.out.println("tonecount"+targetOneCount);
				}
			else if(targetOneCount >0)
				{section2 = (targetOneCount/partitionSize)*log2(partitionSize/targetOneCount);
				//System.out.println("section2"+section2);
				}
			entropy =(section1)+( section2);
			//System.out.println("Entropy - "+entropy);
			double gain =0.0;
			for(int d=0;d<featureCount;d++)
			{
				gainArray[d][k] =entropy - entropyCn[d]; 
				//System.out.println("Gain1 - "+gaina0[k]);
				
			}
				
						
			//gaina3[k] =entropy - entropyCn[3];
			//gaina3[k] =entropy - entropyCn[4];
			//System.out.println("Gain3 - "+gaina2[k]);
			
			//sortGain(gainArray);
			gainArrayCopy = Arrays.copyOf(gainArray, gainArray.length);
			Arrays.sort(gainArray[k]);
			gain = gainArray[featureCount-1][k];
			/*if(gaina0[k]>=gaina1[k] && gaina0[k]>=gaina2[k])
				gain = gaina0[k];
			else if(gaina1[k]>=gaina2[k] && gaina1[k]>=gaina0[k])
				gain = gaina1[k];
			else if(gaina2[k]>=gaina1[k] && gaina2[k]>=gaina0[k])
				gain = gaina2[k];*/
			//System.out.println("Gain "+gain);
			F[k] = (partitionSize/num_Of_Instances)*gain;
			//System.out.println("F - "+F[k]);
			
		}
		double partId=0.0;
		FCopy = Arrays.copyOf(F, F.length);
		Arrays.sort(F);
		for(int r=0;r<FCopy.length;r++)
		{
			if(FCopy[r]==F[F.length-1])
			{
				partId = r+1;
			}
		}
		
		System.out.println("Partition to be replaced is "+ partId);
		double tempGain;
		for(int g=0;g<partitionList.size();g++)
		{
			if(g== partId -1)
			{	
				tempGain = gainArray[featureCount-1][g];
			}
		}

		/*
		if(F[0]>=F[1] && F[0]>=F[2] && F[0]>=F[3])
			{
			System.out.println("Partition to be replaced is "+ partitionList.get(0).partitionId);
		partId = 0;
			}
		else if(F[1]>=F[2] && F[1]>=F[0] && F[0]>=F[3])
				{System.out.println("Partition to be replaced is "+ partitionList.get(1).partitionId);
				partId=1;
				}
		else if(F[2]>=F[1] && F[2]>=F[0] && F[0]>=F[3])
			{System.out.println("Partition to be replaced is "+ partitionList.get(2).partitionId);
			partId=2;
			}
		else if(F[3]>=F[0] && F[3]>=F[1] && F[0]>=F[2])
		{
			System.out.println("Partition to be replaced is "+ partitionList.get(3).partitionId);
		partId=3;
		
			
		}*/
		
		
		
		int newInstanceId=0;
		Partition parta0 = new Partition();
		parta0.values = new ArrayList<Integer>();
		Partition parta1 = new Partition();
		parta1.values = new ArrayList<Integer>();
		Partition parta2 = new Partition();
		parta2.values = new ArrayList<Integer>();
		String feature;
		for(int g=0;g<instanceList.size();g++)
		{
			newInstanceId++;
			if(partId==0)
			{	
				for(int p=0;p<partitionList.get(0).values.size();p++)
				{
			if(partitionList.get(0).values.get(p) ==newInstanceId)
			{
			if(feature.equals("a0"))
			{
				if(instanceList.get(g).a0 == 0)
				{
				parta0.partitionId = partitionList.get(0).partitionId+"0";
				
				parta0.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a0 == 1)
				{
				parta1.partitionId = partitionList.get(0).partitionId+"1";
				parta1.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a0 == 2)
				{
				parta2.partitionId = partitionList.get(0).partitionId+"2";
				parta2.values.add(newInstanceId);
				}
			}
			else if(feature.equals("a1"))
			{
				if(instanceList.get(g).a1 == 0)
				{
				parta0.partitionId = partitionList.get(0).partitionId+"0";
				
				parta0.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a1 == 1)
				{
				parta1.partitionId = partitionList.get(0).partitionId+"1";
				parta1.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a1 == 2)
				{
				parta2.partitionId = partitionList.get(0).partitionId+"2";
				parta2.values.add(newInstanceId);
				}
			}
			else if(feature.equals("a2"))
			{
				if(instanceList.get(g).a2 == 0)
				{
				parta0.partitionId = partitionList.get(0).partitionId+"0";
				
				parta0.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a2 == 1)
				{
				parta1.partitionId = partitionList.get(0).partitionId+"1";
				parta1.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a2 == 2)
				{
				parta2.partitionId = partitionList.get(0).partitionId+"2";
				parta2.values.add(newInstanceId);
				}
			}
			}}
			}
			else if(partId==1)
			{
				//System.out.println("gets here");
				for(int f=0;f<partitionList.get(1).values.size();f++)
				{
			if(partitionList.get(0).values.get(f) ==newInstanceId)
			{
			if(feature.equals("a0"))
			{
				if(instanceList.get(g).a0 == 0)
				{
				parta0.partitionId = partitionList.get(1).partitionId+"0";
				
				parta0.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a0 == 1)
				{
				parta1.partitionId = partitionList.get(1).partitionId+"1";
				parta1.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a0 == 2)
				{
				parta2.partitionId = partitionList.get(1).partitionId+"2";
				parta2.values.add(newInstanceId);
				}
			}
			else if(feature.equals("a1"))
			{
				if(instanceList.get(g).a1 == 0)
				{
				parta0.partitionId = partitionList.get(1).partitionId+"0";
				
				parta0.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a1 == 1)
				{
				parta1.partitionId = partitionList.get(1).partitionId+"1";
				parta1.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a1 == 2)
				{
				parta2.partitionId = partitionList.get(1).partitionId+"2";
				parta2.values.add(newInstanceId);
				}
			}
			else if(feature.equals("a2"))
			{
				if(instanceList.get(g).a2 == 0)
				{
				parta0.partitionId = partitionList.get(1).partitionId+"0";
				
				parta0.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a2 == 1)
				{
				parta1.partitionId = partitionList.get(1).partitionId+"1";
				parta1.values.add(newInstanceId);
				}
				else if(instanceList.get(g).a2 == 2)
				{
				parta2.partitionId = partitionList.get(1).partitionId+"2";
				parta2.values.add(newInstanceId);
				}
			}
			}}
			}
			else if(partId==2)
			{
				//System.out.println("actually should get here!");
				for(int q=0;q<partitionList.get(2).values.size();q++)
				{
				if(partitionList.get(2).values.get(q) ==newInstanceId)
					{
					if(feature.equals("a0"))
					
					{
						if(instanceList.get(g).a0 == 0)
						{
						parta0.partitionId = partitionList.get(2).partitionId+"0";
						
						parta0.values.add(newInstanceId);
						}
						else if(instanceList.get(g).a0 == 1)
						{
						parta1.partitionId = partitionList.get(2).partitionId+"1";
						parta1.values.add(newInstanceId);
						}
						else if(instanceList.get(g).a0 == 2)
						{
						parta2.partitionId = partitionList.get(2).partitionId+"2";
						parta2.values.add(newInstanceId);
						}
					}	
					else if(feature.equals("a1"))
					{
						
						if(instanceList.get(g).a1 == 0)
						{
						parta0.partitionId = partitionList.get(2).partitionId+"0";
					//	System.out.println("Works fine");
						parta0.values.add(newInstanceId);
						}
						else if(instanceList.get(g).a1 == 1)
						{
						parta1.partitionId = partitionList.get(2).partitionId+"1";
						parta1.values.add(newInstanceId);
						}
						else if(instanceList.get(g).a1 == 2)
						{
						parta2.partitionId = partitionList.get(2).partitionId+"2";
						parta2.values.add(newInstanceId);
						}
					}
					else if(feature.equals("a2"))
					{
						if(instanceList.get(g).a2 == 0)
						{
						parta0.partitionId = partitionList.get(2).partitionId+"0";
						
						parta0.values.add(newInstanceId);
						}
						else if(instanceList.get(g).a2 == 1)
						{
						parta1.partitionId = partitionList.get(2).partitionId+"1";
						parta1.values.add(newInstanceId);
						}
						else if(instanceList.get(g).a2 == 2)
						{
						parta2.partitionId = partitionList.get(2).partitionId+"2";
						parta2.values.add(newInstanceId);
						}
					}
					}
				}
			}
		}
		System.out.println("Output partition:");
		System.out.println(parta0.partitionId);
		for(int h=0;h<parta0.values.size();h++)
		System.out.println(parta0.values.get(h));
		
		System.out.println(parta1.partitionId);
		for(int r=0;r<parta1.values.size();r++)
		System.out.println(parta1.values.get(r));
		
		System.out.println(parta2.partitionId);
		for(int d=0;d<parta2.values.size();d++)
		System.out.println(parta2.values.get(d));
				}
		
	}
	
	
	private void sortGain() {
		
		
	}

	public static double log2(double n)
	{
	    return (Math.log(n) / Math.log(2));
	}
//Read contents from input and evidence file
	private void readFile() {
		try {
			
			 Scanner sn = new Scanner(new File(inputFile));
			 Scanner snp = new Scanner(new File(partitionFile));
			 num_Of_Instances = sn.nextInt();
			 //System.out.println(num_Of_Instances);
			 featureCount = sn.nextInt();
			 //System.out.println(featureCount);
			 instanceList = new ArrayList<Entry>();
			 
			 for(int i=0;i<num_Of_Instances;i++)
			 {
				 Entry entry = new Entry();
				 
				 for(int x=0;x<featureCount;x++)
					 {
					 Feature feature = new Feature();
					 feature.featureID = "a"+x;
					 feature.featureValue = sn.nextInt();
					 entry.features.add(feature);
					 }
				 
				 
				 entry.targetAtr = sn.nextInt();
				 instanceList.add(entry);
			 }
			 /*
			 for(int d=0;d<instanceList.size();d++)
			 {
				 System.out.println();
				System.out.print(instanceList.get(d).a0); 
				System.out.print(instanceList.get(d).a1);
				System.out.print(instanceList.get(d).a2);
				System.out.print(instanceList.get(d).targetAtr);
			 }*/
			BufferedReader br = new BufferedReader(new FileReader(partitionFile));
			lines = new ArrayList<String>();
			while((strLine = br.readLine())!=null)
	        {	
	        	lines.add(strLine);
	        }
			partitionList = new ArrayList<Partition>();
			for(int j=0;j<lines.size();j++)
			{
				Partition pr = new Partition(); 
				//int temp = snp.nextInt();
				pr.partitionId = snp.next();
				pr.values = new ArrayList<Integer>();
				while(snp.hasNextInt())
				{
				pr.values.add(snp.nextInt());
				}
				partitionList.add(pr);
			}
			/*
			for(int y=0;y<partitionList.size();y++)
			{
				System.out.println(partitionList.get(y).partitionId); 
				for(int z=0;z<partitionList.get(y).values.size();z++)
				{
					System.out.print(partitionList.get(y).values.get(z));
				}
			}*/
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

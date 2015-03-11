import java.util.ArrayList;
import java.util.List;


public class Entry {
	int a0;
	int a1;
	int a2;
	int a3;
	int a4;
	List<Feature> features = new ArrayList<Feature>();
	int targetAtr;
	public class Feature
	{
		String featureID;
		int featureValue;
	}
}

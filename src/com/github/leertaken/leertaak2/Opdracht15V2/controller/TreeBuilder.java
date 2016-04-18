package com.github.leertaken.leertaak2.Opdracht15V2.controller;

import com.github.leertaken.leertaak2.Opdracht15V2.classifier.DecisionTree;
import com.github.leertaken.leertaak2.Opdracht15V2.classifier.Feature;
import com.github.leertaken.leertaak2.Opdracht15V2.classifier.FeatureType;
import com.github.leertaken.leertaak2.Opdracht15V2.classifier.Item;

import java.io.FileReader;
import java.util.*;

/**
 * Created by Michs on 8-3-2016.
 */
public class TreeBuilder {
    Map<Item,String> trainingSet = new HashMap<>();
    Map<String, FeatureType> featureSet = new HashMap<>();
    ArrayList<String> featureNames = new ArrayList<>();

    String[] featureTypes = null;

    FeatureType type = new FeatureType("TrueFalse",new String[]{"1","0"});

    int numberItems = 0;
    int numberFeatures = 0;

    private FileReader fileReader,fileReaderFeatures;




    public TreeBuilder(FileReader fileReader,FileReader fileReaderFeatures){
                       //FileReader fileReaderFeatures) {
        this.fileReader = fileReader;
        this.fileReaderFeatures = fileReaderFeatures;
    }

    public DecisionTree buildTree(){
        Scanner scanner;
        try{
            FileReader features = fileReaderFeatures;
            Scanner featureScan = new Scanner(features);
            int featureId = 0;
            ArrayList<String> featureArray = new ArrayList();

            String[] featureTypesString=null;
            while (featureScan.hasNext()) {
                String typeString = featureScan.nextLine();
                featureArray.add(typeString);
                featureTypesString = new String[featureArray.size()];
                featureTypesString = featureArray.toArray(featureTypesString);
                FeatureType featureType = new FeatureType(typeString, featureTypesString);
                featureSet.put(typeString, type);
                featureNames.add(typeString);
                featureId++;
            }
            //type = new FeatureType("",featureTypesString);


            scanner = new Scanner(fileReader);
            scanner.useDelimiter(";");

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();

                String[] onderdelen = line.split(";");
                if(onderdelen[0].equals("Features")){
                    numberFeatures = Integer.parseInt(onderdelen[1]);
                    System.out.println(numberFeatures+" number of features.");
                }
                else if (onderdelen[0].equals("Items")){
                    numberItems = Integer.parseInt(onderdelen[1]);
                    System.out.println(numberItems+ " number of items" );
                }
                else {
                    Feature[] featureValues = new Feature[numberFeatures];

                    for(int i = 0; i<onderdelen.length-2;i++){
                        String value = onderdelen[i+1];

                        if(featureTypes ==null){
                            if(onderdelen[i+1].equals("1")){
                                value = "1";
                            }
                            if(onderdelen[i+1].equals("0")){
                                value = "0";
                            }
                        }else{
                            value = featureTypes[Integer.parseInt(onderdelen[i+1])];
                        }
                        featureValues[i] = new Feature(featureNames.get(i),value,type);
                        //System.out.println(type);
                    }
                    Item item = new Item(onderdelen[0],featureValues);

                    trainingSet.put(item,onderdelen[onderdelen.length-1]);

                }
            }
        }
        catch (Exception e){e.printStackTrace();}
        System.out.println(trainingSet.size() + "Size Trainingset ");
        System.out.println(featureSet.size() + "Size FeatureSet");
        DecisionTree decisionTree = new DecisionTree(trainingSet,featureSet);
        return decisionTree;
    }
}

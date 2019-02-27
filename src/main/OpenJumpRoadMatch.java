package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JWindow;
import javax.swing.JComponent;

import com.vividsolutions.jump.coordsys.CoordinateSystem;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.io.datasource.Connection;
import com.vividsolutions.jump.io.datasource.DataSource;
import com.vividsolutions.jump.io.datasource.DataSourceQuery;
import com.vividsolutions.jump.io.datasource.StandardReaderWriterFileDataSource.Shapefile;
import com.vividsolutions.jump.util.LangUtil;
import com.vividsolutions.jump.task.TaskMonitor;
import com.vividsolutions.jump.workbench.ui.GUIUtil;
import com.vividsolutions.jump.workbench.ui.SplashWindow;
import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.JUMPWorkbench;;

public class OpenJumpRoadMatch {
	
	public Shapefile dataSourceClass = null;
	public static CoordinateSystem coordinateSystem = CoordinateSystem.UNSPECIFIED;
	//public static File file = new File("D:\\WIP\\Project Roadmatcher\\Java\\data\\sample\\dataset1.shp");
	public static File file = new File("D:\\WIP\\Project_Frontages_and_Setbacks\\InputDatasets\\Small_Dataset\\Shapefile_output\\Small_ST_line.shp");
	

	public static void main(String[] args) {
		//DataSource
		DataSource dataSource = getDataSource();                

        //Datasource query.		
		DataSourceQuery dataSourceQuery = getDataSourceQuery(dataSource, coordinateSystem);               
        
        // feature collection.      
		FeatureCollection featureCollection = getFeatureCollection(dataSourceQuery);
		
		//Create Workbench
		//createWorkBenchContext();
		
		//Print Results
		printResults( featureCollection);
	}
	
	public static DataSource getDataSource() {		
        Shapefile dataSourceClass = new Shapefile();
		DataSource dataSource = (DataSource) LangUtil.newInstance(dataSourceClass.getClass()); 
		
		return dataSource;
	}
	
	public static DataSourceQuery getDataSourceQuery(DataSource dataSource, CoordinateSystem coordinateSystem ) {	
		Map<String, Object> options = new HashMap<String, Object>();
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(DataSource.FILE_KEY, file.getPath());
        properties.put(DataSource.COORDINATE_SYSTEM_KEY, coordinateSystem.getName());
        properties.putAll(options);
        dataSource.setProperties(properties);       
        String layerName = GUIUtil.nameWithoutExtension(file) ;
        DataSourceQuery dataSourceQuery = new DataSourceQuery(dataSource, null, layerName); 
        
		return dataSourceQuery;
	}
	
	public static FeatureCollection getFeatureCollection(DataSourceQuery dataSourceQuery) {
		Connection connection = dataSourceQuery.getDataSource().getConnection();
        ArrayList queryExceptions  = new ArrayList();
        TaskMonitor taskMonitor =  null; 
        FeatureCollection featureCollection  = connection.executeQuery(dataSourceQuery.getQuery(), queryExceptions, taskMonitor);
		return featureCollection;
	}

	public static void printResults(FeatureCollection featureCollection) {
		System.out.println(featureCollection);
        System.out.println(featureCollection.getFeatures().size());
        System.out.println(featureCollection.getFeatures().get(0).getAttribute("STREET"));
        System.out.println(featureCollection.getFeatureSchema().getCoordinateSystem());
        for (String attribute : featureCollection.getFeatureSchema().getAttributeNames()) {
            System.out.println(attribute);
        }
        System.out.println("(" + featureCollection.getEnvelope().getMaxX() + "," + featureCollection.getEnvelope().getMaxY() + ")");
        System.out.println("(" + featureCollection.getEnvelope().getMinX() + "," + featureCollection.getEnvelope().getMinY() + ")");
	}
	/*
	public static void createWorkBenchContext() {
		SplashWindow splashWindow = null;
		TaskMonitor monitor = ; 
	    JUMPWorkbench workbench = new JUMPWorkbench("title", "{args}", splashWindow, monitor);
		WorkbenchContext workbenchContext = workbench.getContext();
	}*/
}

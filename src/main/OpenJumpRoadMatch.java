package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.vividsolutions.jump.coordsys.CoordinateSystem;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.io.datasource.Connection;
import com.vividsolutions.jump.io.datasource.DataSource;
import com.vividsolutions.jump.io.datasource.DataSourceQuery;
import com.vividsolutions.jump.io.datasource.StandardReaderWriterFileDataSource.Shapefile;
import com.vividsolutions.jump.util.LangUtil;
import com.vividsolutions.jump.task.TaskMonitor;
import com.vividsolutions.jump.workbench.ui.GUIUtil;

public class OpenJumpRoadMatch {

	public static void main(String[] args) {
		//DataSource
        File file = new File("D:\\WIP\\Project Roadmatcher\\Java\\data\\sample\\dataset1.shp");
        Shapefile dataSourceClass = new Shapefile();
        DataSource dataSource = (DataSource) LangUtil.newInstance(dataSourceClass.getClass());          
        
        //Datasource query.
        Map<String, Object> options = new HashMap<String, Object>();
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(DataSource.FILE_KEY, file.getPath());
        properties.put(DataSource.COORDINATE_SYSTEM_KEY, CoordinateSystem.UNSPECIFIED);
        properties.putAll(options);
        dataSource.setProperties(properties);       
        String layerName = GUIUtil.nameWithoutExtension(file) ;
        DataSourceQuery dataSourceQuery = new DataSourceQuery(dataSource, null, layerName);        
        
        // feature collection.      
        Connection connection = dataSourceQuery.getDataSource().getConnection();
        ArrayList queryExceptions  = new ArrayList();
        TaskMonitor taskMonitor =  null; 
        FeatureCollection featureCollection  = connection.executeQuery(dataSourceQuery.getQuery(), queryExceptions, taskMonitor);
        System.out.println(featureCollection);
        System.out.println(featureCollection.getFeatures().size());
        System.out.println(featureCollection.getFeatureSchema().getCoordinateSystem());

	}

}

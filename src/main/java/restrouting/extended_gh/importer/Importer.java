package restrouting.extended_gh.importer;

import restrouting.extended_gh.importer.models.Base;
import restrouting.extended_gh.importer.models.GeoPoint;
import restrouting.extended_gh.importer.models.Intersection;
import restrouting.extended_gh.importer.models.Section;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Manal on 16.09.2016.
 */

public class Importer {

    private static final String RELATIVE_TARGET_DIRECTORY = "./data-converted/csv";

    public static void main(String[] args) {
        Long newId = 0L;

        Map<String, Section> sections = new HashMap<String, Section>();
        Map<String, Intersection> intersections = new HashMap<String, Intersection>();
        List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();

        // coordinates.csv

        try {
            File file = new File("data/coordinates.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); // Skip first line.
            String line;
            long lastTime = System.currentTimeMillis();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String sid = parts[2];
                String[] x = parts[1].split(",");

                Section s = new Section();
                s.setId(newId++);
                s.setSid(sid);

                //set timestamp for geopoints
                int sec = 10;
                Date date= new java.util.Date();
                Timestamp  now = new Timestamp(date.getTime());
                System.out.println(now);

                for (int i = 0; i < x.length - 2; i += 2) {
                    float lon = Float.parseFloat(x[i+0]);
                    float lat = Float.parseFloat(x[i+1]);
                    GeoPoint gp = new GeoPoint(newId++, lat, lon);
                    gp.setTimestamp(now);
                    s.addGeoPoint(gp);
                    geoPoints.add(gp);
                    i += 1;
                    Timestamp later = new Timestamp(now.getTime() + (sec++ *1000L));
                    now = later;
                }
                sections.put(sid, s);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // weights

        try {
            File file = new File("data/weights");
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); // Skip first line.
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String sid = parts[0];
                int sperr = Integer.parseInt(parts[1]);
                int l = Integer.parseInt(parts[2]);
                int t = Integer.parseInt(parts[3]);
                int tRout = Integer.parseInt(parts[4]);

                Section s = (Section)sections.get(sid);

                if (s != null) {
                    // VS1
                    // Update Section object in hashmap.
                    // We don't need to put it back in the map,
                    // since we are working with references.
                    s.setSperr(sperr);
                    s.setL(l);
                    s.setT(t);
                    s.setTRout(tRout);
                } else {
                    // VS2
                    Intersection is = new Intersection();
                    is.setId(newId++);
                    is.setSid(sid);
                    is.setSperr(sperr);
                    is.setL(l);
                    is.setT(t);
                    is.setTRout(tRout);
                    intersections.put(sid, is);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // connections

        try {
            File file = new File("data/connections");
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); // Skip first line.
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String sidFrom = parts[0];
                String sidTo = parts[1];

                Base from;
                from = sections.get(sidFrom);
                if (from != null) {
                } else {
                    from = intersections.get(sidFrom);
                }

                Base to;
                to = sections.get(sidTo);
                if (to == null) {
                    to = intersections.get(sidTo);
                }

                from.addConnection(to);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // Nodes

        try {
            PrintWriter writer = new PrintWriter(RELATIVE_TARGET_DIRECTORY + "/nodes_sections.csv", "UTF-8");
            writer.println(Section.getCsvHeader());
            for (Section s : sections.values()) {
                    writer.println(s.getCsvLine());
                }

            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            PrintWriter writer = new PrintWriter(RELATIVE_TARGET_DIRECTORY + "/nodes_intersections.csv", "UTF-8");
            writer.println(Intersection.getCsvHeader());
            for (Intersection is : intersections.values()) {
                writer.println(is.getCsvLine());
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            PrintWriter writer = new PrintWriter(RELATIVE_TARGET_DIRECTORY + "/nodes_geopoints.csv", "UTF-8");
            writer.println(GeoPoint.getCsvHeader());
            for (GeoPoint gp : geoPoints) {
                writer.println(gp.getCsvLine());
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Insections

        try {
            PrintWriter writer = new PrintWriter(RELATIVE_TARGET_DIRECTORY + "/relations_sections_intersetions.csv", "UTF-8");
            writer.println("id_section\tid_intersection\ttype");
            for (Section s : sections.values()) {
                for (String l : s.relationsIntersections()) {
                    writer.println(l);
                }
            }
            for (Intersection is : intersections.values()) {
                for (String l : is.relationsSections()) {
                    writer.println(l);
                }
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            PrintWriter writer = new PrintWriter(RELATIVE_TARGET_DIRECTORY + "/relations_sections_geopoints.csv", "UTF-8");
            writer.println("id_section\tid_geopoints\ttype");
            for (Section s : sections.values()) {
                for (String l : s.relationsGridPoints()) {
                    writer.println(l);
                }
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            PrintWriter writer = new PrintWriter(RELATIVE_TARGET_DIRECTORY + "/nodes_sections_geopoints2.csv", "UTF-8");
            writer.println(Section.getCsvHeader() + "\t"+ GeoPoint.getCsvHeader());
            for (Section s : sections.values()) {
                for(Base entry : s.getGeoPoints()) {
                    GeoPoint geoPoint = (GeoPoint) entry;
                        writer.println(s.getCsvLine() + "\t"+ geoPoint.getCsvLine());
                    }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.lytlogic.simulate;

import java.util.List;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Record {
    public List<World> worlds;

    public List<RecordData> records = new ArrayList();

    public class RecordData {
        public int n;
        public int day;
        public List<Integer> nInfected = new ArrayList();
        public List<Integer> nExposed = new ArrayList();
        public List<Integer> nIsolated = new ArrayList();
        public World world;

        public RecordData(World w, int d) {
            world = w;
            day = d;
            n = world.persons.size();
        }

        public void collect() {
            nInfected.add(world.nInfected);
            nIsolated.add(world.nIsolated);
            nExposed.add(world.nExposed);
        }

        public void run() {
            for (int i = 0; i < day; i++) {
                world.go();
                collect();
            }
        }
    }

    public Record(List<World> ws, int day) {
        worlds = ws;
        for (World w : worlds) {
            RecordData record = new RecordData(w, day);
            records.add(record);
        }
    }

    public void run() {
        for (int i = 0; i < records.size(); i++) {
            System.out.println(i);
            records.get(i).run();
        }
    }

    public void write(String file) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            PrintStream p = new PrintStream(out);
            System.out.println("writing...");
            p.println(records.size());
            for (RecordData r : records) {
                p.println(r.n);
                p.println(r.nInfected.stream().map(String::valueOf).collect(Collectors.joining(" ")));
                p.println(r.nExposed.stream().map(String::valueOf).collect(Collectors.joining(" ")));
                p.println(r.nIsolated.stream().map(String::valueOf).collect(Collectors.joining(" ")));
            }
            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
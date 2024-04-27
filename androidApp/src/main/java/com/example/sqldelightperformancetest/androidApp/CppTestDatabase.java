package com.example.sqldelightperformancetest.androidApp;

import java.util.ArrayList;
import comexampledb.Project;

public class CppTestDatabase {

    public static native void createProjects();

    public static native ArrayList<Project> fetchProjects();

}

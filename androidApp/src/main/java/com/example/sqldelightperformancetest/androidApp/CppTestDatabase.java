package com.example.sqldelightperformancetest.androidApp;

import java.util.ArrayList;
import com.example.sqldelightperformancetest.shared.Project;

public class CppTestDatabase {

    public static native void createProjects();

    public static native ArrayList<Project> fetchProjects();

}

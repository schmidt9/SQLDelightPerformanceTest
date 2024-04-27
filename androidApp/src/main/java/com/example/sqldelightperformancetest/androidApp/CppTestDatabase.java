package com.example.sqldelightperformancetest.androidApp;

import com.example.sqldelightperformancetest.shared.DatabaseProject;

import java.util.ArrayList;

public class CppTestDatabase {

    public static native void createProjects();

    public static native ArrayList<DatabaseProject> fetchProjects();

}

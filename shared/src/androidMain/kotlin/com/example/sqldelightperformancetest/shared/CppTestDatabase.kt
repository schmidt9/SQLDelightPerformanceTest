package com.example.sqldelightperformancetest.shared;

import comexampledb.Project

class CppTestDatabase {

    external fun createProjects(databasePath: String, count: Int);

    external fun createImageProjects(databasePath: String, count: Int, imageData: ByteArray);

    external fun fetchProjects(databasePath: String) : ArrayList<Project>;

}

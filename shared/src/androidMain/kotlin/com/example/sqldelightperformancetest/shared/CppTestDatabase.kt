package com.example.sqldelightperformancetest.shared;

import comexampledb.Project

class CppTestDatabase {

    external fun createProjects();

    external fun fetchProjects() : ArrayList<Project>;

}

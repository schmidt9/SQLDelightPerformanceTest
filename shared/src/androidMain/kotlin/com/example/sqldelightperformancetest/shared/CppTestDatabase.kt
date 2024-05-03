package com.example.sqldelightperformancetest.shared;

import comexampledb.Project

class CppTestDatabase {

    external fun createProjects(count: Int);

    external fun fetchProjects() : ArrayList<Project>;

}

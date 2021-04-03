//
//  ViewController.swift
//  iosApp
//
//  Created by Alexander Kormanovsky on 02.04.2021.
//

import UIKit
import SQLDelightPerformanceTest

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let dbPath = FileManager.default.urls(for: .applicationSupportDirectory, in: .userDomainMask).first!.appendingPathComponent("databases").appendingPathComponent("test.db")
        print("SQLDelight test database path", dbPath.path)
        
        print("\n=== Kotlin ===\n")

        print("start createProjects()")
        var startDate = Date()
        DatabaseTest().createProjects()
        var endDate = Date()
        print("createProjects() elapsed time", endDate.timeIntervalSince(startDate))

        print("start fetchProjects()")
        startDate = Date()
        let kotlinProjects = DatabaseTest().fetchProjects()
        endDate = Date()
        print("fetchProjects() elapsed time \(endDate.timeIntervalSince(startDate)), count: \(kotlinProjects.count)")
        
        print("\n=== CPP ===\n")
        
        print("start createProjects()")
        startDate = Date()
        TDTestDatabaseBridge.createProjects(withDatabasePath: dbPath.path);
        endDate = Date()
        print("createProjects() elapsed time", endDate.timeIntervalSince(startDate))
        
        print("start fetchProjects()")
        startDate = Date()
        let cppProjects = TDTestDatabaseBridge.fetchProjects(withDatabasePath: dbPath.path)!
        endDate = Date()
        print("fetchProjects() elapsed time \(endDate.timeIntervalSince(startDate)), count: \(cppProjects.count)")
        
    }
    


}


//
//  ViewController.swift
//  iosApp
//
//  Created by Alexander Kormanovsky on 02.04.2021.
//

import UIKit
import SQLDelightPerformanceTest

class ViewController: UIViewController {
    
    @IBOutlet var logTextView: UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        var logString = ""
        
        let dbPath = FileManager.default.urls(for: .applicationSupportDirectory, in: .userDomainMask).first!.appendingPathComponent("databases").appendingPathComponent("test.db")
        logString = "SQLDelight test database path \(dbPath.path)\n"
        
        logString += "\n=== Kotlin ===\n"

        logString += "start createProjects() - INSERT\n"
        var startDate = Date()
        DatabaseTest().createProjects()
        var endDate = Date()
        logString += "createProjects() elapsed time \(endDate.timeIntervalSince(startDate))\n"

        logString += "start fetchProjects() - SELECT\n"
        startDate = Date()
        let kotlinProjects = DatabaseTest().fetchProjects()
        endDate = Date()
        logString += "fetchProjects() elapsed time \(endDate.timeIntervalSince(startDate)), count: \(kotlinProjects.count)\n"
        
        logString += "\n=== CPP ===\n"
        
        logString += "start createProjects() - INSERT\n"
        startDate = Date()
        TDTestDatabaseBridge.createProjects(withDatabasePath: dbPath.path);
        endDate = Date()
        logString += "createProjects() elapsed time, \(endDate.timeIntervalSince(startDate))\n"
        
        logString += "start fetchProjects() - SELECT\n"
        startDate = Date()
        let cppProjects = TDTestDatabaseBridge.fetchProjects(withDatabasePath: dbPath.path)!
        endDate = Date()
        logString += "fetchProjects() elapsed time \(endDate.timeIntervalSince(startDate)), count: \(cppProjects.count)\n"
        
        print(logString)
        
        logTextView.text = logString
        
    }

}


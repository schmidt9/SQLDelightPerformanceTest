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
        
        let dbPath = FileManager.default.urls(for: .applicationSupportDirectory, in: .userDomainMask).first!.appendingPathComponent("databases")
        print("SQLDelight databases path", dbPath.path)
        
        print("start createProjects()")
        var startDate = Date()
        DatabaseTest().createProjects()
        var endDate = Date()
        print("createProjects() elapsed time", endDate.timeIntervalSince(startDate))
        
        print("start fetchProjects()")
        startDate = Date()
        let projects = DatabaseTest().fetchProjects()
        endDate = Date()
        print("fetchProjects() elapsed time \(endDate.timeIntervalSince(startDate)), count: \(projects.count)")
    }


}


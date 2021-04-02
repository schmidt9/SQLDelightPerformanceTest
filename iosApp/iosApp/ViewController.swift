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
        
        let platform = Platform().platform
        print(platform)
    }


}


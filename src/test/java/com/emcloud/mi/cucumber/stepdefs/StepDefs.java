package com.emcloud.mi.cucumber.stepdefs;

import com.emcloud.mi.EmCloudMiApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = EmCloudMiApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}

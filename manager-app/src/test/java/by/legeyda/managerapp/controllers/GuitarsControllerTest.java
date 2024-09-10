package by.legeyda.managerapp.controllers;

import by.legeyda.managerapp.client.GuitarsRestClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class GuitarsControllerTest {

    @Mock
    GuitarsRestClient guitarsRestClient;

    @InjectMocks
    GuitarsController controller;


    @Test
    void getGuitarsList() {
    }

    @Test
    void getNewGuitarPage() {
    }

    @Test
    void createGuitar() {


    }
}
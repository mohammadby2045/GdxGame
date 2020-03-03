package com.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(GdxRunnerTest.class)
public class CameraTest {

    private Camera camera = new Camera();

    @Before
    public void init() {
        Gdx.graphics = mock(Graphics.class);
        Gdx.input = mock(Input.class);
    }

    @Test
    public void testCreateCamera_ShouldSucceed() {
        given(Gdx.graphics.getWidth()).willReturn(800);
        given(Gdx.graphics.getHeight()).willReturn(600);

        OrthographicCamera orthographicCamera = camera.createCamera();

        assertThat(orthographicCamera).isNotNull();
        assertThat(orthographicCamera.zoom).isEqualTo(.4f);
    }

    @Test
    public void testInsertControl_ShouldSucceed() {
        given(Gdx.graphics.getWidth()).willReturn(800);
        given(Gdx.graphics.getHeight()).willReturn(600);

        OrthographicCamera orthographicCamera = camera.createCamera();
        Control control = camera.insertControl(orthographicCamera);

        assertThat(control).isNotNull();
        assertThat(control.getScreenWidth()).isEqualTo((int) orthographicCamera.viewportWidth);
        assertThat(control.getScreenHeight()).isEqualTo((int) orthographicCamera.viewportHeight);
    }
}
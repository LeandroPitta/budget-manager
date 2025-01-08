package com.leandropitta.budget_manager.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class GifUtil {

    public static String getBudgetGifUrl(String gifId) {
        String gifPath = String.format("assets/gifs/budget_gif/%s.gif", gifId);
        if (GifUtil.class.getClassLoader().getResource(gifPath) != null) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            return String.format("%s/assets/gifs/budget_gif/%s.gif", baseUrl, gifId);
        } else {
            return gifId;
        }
    }

    public static String getBackgroundGifUrl(Long gifId) {
        String gifPath = String.format("assets/gifs/background_gif/%s.gif", gifId);
        if (GifUtil.class.getClassLoader().getResource(gifPath) != null) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            return String.format("%s/assets/gifs/background_gif/%s.gif", baseUrl, gifId);
        } else {
            return null;
        }
    }
}
package com.slmas.Sl.repository;

import com.slmas.Sl.domain.Guide;

public interface GuideRepository {
    public Integer createGuide(Guide guide);
    public Integer updateGuide(Guide guide);
}

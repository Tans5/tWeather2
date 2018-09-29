package com.tans.tweather2.repository.resource

import com.tans.tweather2.db.CityDao
import javax.inject.Inject

class LocalResource @Inject constructor(private val cityDao: CityDao)
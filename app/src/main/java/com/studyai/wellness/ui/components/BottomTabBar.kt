package com.studyai.wellness.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyai.wellness.ui.theme.PrimaryGreen

sealed class BottomTab(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Dashboard : BottomTab("dashboard", Icons.Filled.Home, "Home")
    object Calendar : BottomTab("calendar", Icons.Filled.CalendarToday, "Calendar")
    object Stats : BottomTab("stats", Icons.Filled.Star, "Stats")
    object Profile : BottomTab("profile", Icons.Filled.Person, "Profile")
    object Settings : BottomTab("settings", Icons.Filled.Settings, "Settings")

    companion object {
        val tabs = listOf(Dashboard, Calendar, Stats, Profile, Settings)
    }
}

@Composable
fun AppBottomTabBar(
    currentRoute: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp)
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomTab.tabs.forEach { tab ->
            AppTabItem(
                tab = tab,
                isSelected = currentRoute == tab.route,
                onClick = { onTabSelected(tab.route) }
            )
        }
    }
}

@Composable
private fun AppTabItem(
    tab: BottomTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = tab.icon,
            contentDescription = tab.label,
            tint = if (isSelected) PrimaryGreen else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = tab.label,
            color = if (isSelected) PrimaryGreen else Color.Gray,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

import { GraphicEntityModule } from './entity-module/GraphicEntityModule.js'
import { EndScreenModule } from './endscreen-module/EndScreenModule.js'
import { TooltipModule } from './tooltip/TooltipModule.js'

// List of viewer modules that you want to use in your game
export const modules = [
	GraphicEntityModule,
	TooltipModule,
	EndScreenModule
];

export const playerColors = [
	'#3c9fc5', // Green Circle light blue
	'#ff862d', // Green Circle light orange
	'#2782a5', // Green Circle dark blue
	'#d86612', // Green Circle dark orange
	'#de6ddf', // lavender pink
	'#6ac371', // mantis green
	'#9975e2', // medium purple
	'#3ac5ca', // scooter blue
	'#ff1d5c' // radical red
]

export const gameName = 'GreenCircle'

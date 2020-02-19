<template>
  <v-list color="transparent">
    <v-list-group
      v-for="({ hospitalName, hospitalId, poles }, i) in structure"
      :key="i"
      sub-group
      no-action
      value
    >
      <template #activator>
        <v-list-item class="ma-2">
          <v-list-item-avatar>
            <v-avatar class="ma-3" width="60px" height="60px" tile>
              <v-img src="@/assets/logos/icons/list-items/1.png" />
            </v-avatar>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title>
              {{ hospitalName }}
              <v-btn
                icon
                class="ml-1"
                @click="infrastructureAddHandler({link: '/create-sector', hospitalId, leadUnit: false})"
              >
                <v-icon color="black">mdi-plus</v-icon>
              </v-btn>
              <v-btn icon>
                <v-icon
                  color="black"
                  @click="infrastructureDeleteHandler({hospitalId})"
                >mdi-trash-can-outline</v-icon>
              </v-btn>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </template>

      <v-list-group
        v-for="({ poleName, poleId, sectors }, j) in poles"
        :key="j"
        sub-group
        no-action
        value
      >
        <template #activator>
          <v-list-item class="ma-2">
            <v-list-item-avatar>
              <v-avatar class="ma-3" width="60px" height="60px" tile>
                <v-img src="@/assets/logos/icons/list-items/2.png" />
              </v-avatar>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>
                {{ poleName }}
                <v-btn
                  icon
                  @click="infrastructureAddHandler({link: '/create-pole', hospitalId, poleId, leadUnit: false})"
                >
                  <v-icon color="black" class="ml-1">mdi-plus</v-icon>
                </v-btn>
                <v-btn icon>
                  <v-icon
                    color="black"
                    @click="infrastructureDeleteHandler({sectorId})"
                  >mdi-trash-can-outline</v-icon>
                </v-btn>
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>

        <v-list-group
          v-for="({ sectorId, sectorName, staff }, k) in sectors"
          :key="k"
          sub-group
          no-action
          value
          :style="{ 'padding-left': '30px' }"
        >
          <template #activator>
            <v-list-item class="ma-2">
              <v-list-item-avatar>
                <v-avatar class="ma-3" width="60px" height="60px" tile>
                  <v-img src="@/assets/logos/icons/list-items/3.png" />
                </v-avatar>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title>
                  {{ sectorName }}
                  <v-btn icon v-if="sectorAddButton">
                    <v-icon
                      color="black"
                      class="ml-1"
                      @click="infrastructureAddHandler({link: '/affect-staff', hospitalId, poleId, sectorId, leadUnit: false})"
                    >mdi-plus</v-icon>
                  </v-btn>
                  <v-btn icon>
                    <v-icon
                      color="black"
                      @click="infrastructureDeleteHandler({poleId})"
                    >mdi-trash-can-outline</v-icon>
                  </v-btn>
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </template>

          <v-list-item
            v-for="({ label, icon, value }, l) in staff"
            :key="l"
            :style="{ 'padding-left': '125px' }"
          >
            <v-avatar class="ma-2" width="60px" height="60px" tile>
              <v-img :src="icon" />
            </v-avatar>
            <v-list-item-content>
              <v-list-item-title>
                {{ label }}
                <v-btn icon class="ml-1">
                  <v-icon
                    color="black"
                    @click="staffDeleteHandler({link: '/affect-staff', hospitalId, poleId, sectorId, staffId: value, leadUnit: false})"
                  >mdi-trash-can-outline</v-icon>
                </v-btn>
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-group>
      </v-list-group>
    </v-list-group>
  </v-list>
</template>

<script>
export default {
  name: "APHPStructure",
  props: [
    "structure",
    "infrastructureAddHandler",
    "infrastructureDeleteHandler",
    "staffDeleteHandler",
    "sectorAddButton"
  ]
};
</script>

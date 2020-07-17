<!--
  - Licensed to the Apache Software Foundation (ASF) under one or more
  - contributor license agreements.  See the NOTICE file distributed with
  - this work for additional information regarding copyright ownership.
  - The ASF licenses this file to You under the Apache License, Version 2.0
  - (the "License"); you may not use this file except in compliance with
  - the License.  You may obtain a copy of the License at
  -
  -     http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -->

<template>
  <div class="help-content">
    <section class="content">
      <h2>{{ $t('help.design_concept_title') }}</h2>
      <ol>
        <li>{{ $t('help.design_concept_info_1') }}</li>
        <li>{{ $t('help.design_concept_info_2') }}</li>
      </ol>
      <h2>{{ $t('help.major_features_title') }}</h2>
      <ol>
        <li>{{ $t('help.major_features_info_1') }}</li>
        <li>{{ $t('help.major_features_info_2') }}</li>
        <li>{{ $t('help.major_features_info_3') }}</li>
        <li>{{ $t('help.major_features_info_4') }}</li>
        <li>{{ $t('help.major_features_info_5') }}</li>
      </ol>
      <h2>{{ $t('help.unsupported_title') }}</h2>
      <ol>
        <li>{{ $t('help.unsupported_info') }}</li>
      </ol>
    </section>
  </div>
</template>
<style>
  .help-content {
    padding-top: 10px;
  }
  .help-content li {
    list-style: inside;
  }
</style>

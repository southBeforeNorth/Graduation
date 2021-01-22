export default {
  header: {
    welcome: 'Welcome'
  },
  requestForm: {
    header: {
      title: 'Product Change Request System',
      search: 'Search'
    },
    form: {
      header: {
        createTitle: 'Create Request Form',
        changeTitle: 'Change Request Form',
        viewTitle: 'View Request Form',
        status: 'Status',
        lastModifiedDate: 'Last Modification Time',
        formNumber: 'PCRS No.'
      }
    }
  },
  common: {
    action: 'Action',
    view: 'View',
    confirmTitle: 'Confirm',
    preview: 'Preview',
    successText: {
      successFully: 'Successfully!',
      save: 'Save successfully!',
      create: 'Create Successfully!',
      delete: 'Delete Successfully!',
      update: 'Update Successfully!',
      assign: 'Assign Successfully!',
      operate: 'Operate Successfully!'
    },
    status: {
      all: 'All',
      pending: 'Pending',
      scheduled: 'Scheduled',
      active: 'Active',
      completed: 'Completed',
      returned: 'Returned',
      mySubmitted: 'My Submitted'
    },
    button: {
      ok: 'OK',
      add: 'Add',
      save: 'Save',
      edit: 'Edit',
      cancel: 'Cancel',
      submit: 'Submit',
      update: 'Update',
      withdraw: 'Withdraw',
      approve: 'Approve',
      delete: 'Delete',
      return: 'Return',
      close: 'Close Page',
      ignore: 'Ignore',
      copyAsNew: 'Copy as New',
      copyFromExisting: 'Copy from Existing',
      view: 'View',
      search: 'Search',
      reset: 'Reset',
      preview: 'Preview'
    },
    validateMessage: {
      input: 'You must input a non-blank value',
      select: 'You must select a non-blank value'
    },
    warningText: {
      cancelEdit: 'Are you sure to cancel this modification?',
      delete: 'Are you sure to delete this item?',
      add: 'Please save or cancel the new item first!'
    },
    messageBoxTitle: {
      error: 'Error'
    }
  },
  dictionary: {
    addButton: 'New Key',
    name: 'Dictionary Key',
    description: 'Key Description',
    classification: 'Classification',
    createdBy: 'Created By',
    createdTime: 'Create Time',
    lastModifiedBy: 'Modified By',
    lastModifiedTime: 'Modified Time',
    createDictionary: 'Create Dictionary',
    inputMessage: 'You must input a non-blank value.',
    warningText: 'Dictionary Key should unique',
    createSuccess: 'Create Successfully!',
    deleteSuccess: 'Delete Successfully!',
    cancelModal: 'You sure cancel this modification?',
    updateSuccess: 'Update Successfully!',
    deleteModal: 'You sure about deleting this dictionary?',
    dictionaryEmptyText: 'Dictionary Key cannot be empty!',
    validateOverLength: 'You must specify a value length less than 255!',
    addMultipleDictionaryModel: 'Please save or cancel the dictionary added first!'
  },
  dictionaryOptions: {
    key: 'Option name',
    value: 'Value',
    metaValue: 'Meta Value',
    description: 'Value Description',
    sequence: 'Sequence',
    default: 'Default Value',
    optionEmptyText: 'Option name cannot be empty!',
    warningText: 'Option name should unique!',
    validateOverLength: 'You must specify a value length less than or equal to 5!',
    deleteModal: 'You sure about deleting this dictionary option?',
    addMultipleOptionModel: 'Please save or cancel the dictionary option added first!'
  },
  team: {
    title: 'Team',
    columns: {
      name: 'Team Name',
      description: 'Team Description',
      carrier: 'Carrier',
      lastModifiedBy: 'Last Modified By',
      lastModifiedTime: 'Last Modified At'
    },
    newTeam: 'New Team'
  },
  category: {
    title: 'Category Distribution Maintenance',
    placeholder: {
      select: 'Select One',
      input: 'Please Input'
    },
    columns: {
      name: 'Category Name',
      sortSeq: 'Sequence',
      carrier: 'Carrier',
      parent: 'Parent',
      isActive: 'Is Active',
      categoryDistribution: {
        distribution: 'Special Distribution',
        isAllowAdminTask: 'Allow Admin Task',
        isAllowAutoApproval: 'Allow Auto Approval',
        isAllowRiskLevel: 'Allow Risk Level',
        isAllowZones: 'Allow Zones',
        isAllowLocations: 'Allow Locations',
        isAllowService: 'Allow Service',
        isNeedMultipleEndorsers: 'Need Multiple Endorsers',
        isAllowNoTT: 'Allow No TT',
        notifications: 'Notify Others For'
      },
      lastModifiedBy: 'Last Modified By',
      lastModifiedTime: 'Last Modified At'
    },
    newCategory: 'New Category'
  },
  categoryModel: {
    title: {
      createCategory: 'Add Category',
      editCategory: 'Edit Category',
      viewCategory: 'View Category'
    },
    placeholder: {
      categoryName: 'Please input category name',
      parent: 'Please select parent',
      carrier: 'Please select carrier',
      distribution: 'Please input special distribution',
      notifications: 'Please input notify'
    },
    validateMessage: {
      validationLength200Message: 'Input length should less than 200',
      validationLength11Message: 'Input length should less than 11'
    }
  },
  endorser: {
    title: 'Endorsers List',
    columns: {
      name: 'Display Name',
      userId: 'User ID',
      domain: 'Domain',
      lastModifiedBy: 'Last Modified By',
      lastModifiedTime: 'Last Modified At'
    },
    uniqueWarningText: 'Endorser should be unique!'
  },
  requestStatus: {
    title: 'Request Status',
    columns: {
      statusCode: 'Status Code',
      statusDesc: 'Status Description',
      preStatus: 'Previous Status',
      isActive: 'Active',
      sortSeq: 'Sort Seq',
      lastModifiedBy: 'Last Modified By',
      lastModifiedTime: 'Last Modified At'
    },
    newStatus: 'New Status'
  },
  emailTemplate: {
    title: 'Email Template',
    templateName: 'Template Name',
    templateDesc: 'Template Description',
    emailGroup: 'Email Template Group',
    emailFrom: 'Email From',
    emailTO: 'Email To',
    emailCC: 'Email CC',
    emailBCC: 'Email BCC',
    subject: 'Subject',
    emailContent: 'Content',
    addButton: 'New Email Template',
    createTitle: 'Create Email Template',
    editTitle: 'Edit Email Template',
    viewTitle: 'View Email Template',
    previewTitle: 'Preview Email Template',
    validateMessage: {
      templateNameValidationMessage: 'You must specify a value length less than 50!'
    },
    confirmMessage: {
      delete: 'You sure about deleting this email template?'
    }
  },
  validateMessage: {
    selectMessage: 'Please select',
    nonBlank: 'You must input a non-blank value.',
    validationLength50Message: 'You must specify a value length less than 50!',
    validationLengthMessage: 'You must specify a value length less than 256!',
    validationLength512Message: 'You must specify a value length less than 512!'
  },
  request: {
    submission: {
      label: {
        requester: 'Requester',
        submittedDate: 'Submitted Date',
        email: 'E-mail',
        phone: 'Phone'
      },
      validateMessage: {
        phoneLength: 'You must specify a value length less than 20!',
        requester: 'Please input Requester!',
        submittedDate: 'Please input Submitted Date!',
        email: 'Please input Email!'
      }
    },
    changeRequest: {
      title: 'Change Requirement Details',
      anchorTitle: 'Change Request Details',
      impactInfo: {
        title: 'Impact Information',
        note: 'Note: All below three items, if "Yes", please specify unavailable period and Global/Regional user impact',
        label: {
          onlineServiceImpact: 'Online Service Impact',
          applicationImpact: 'Backend Application/Function Impact',
          systemImpact: 'System/Platform Impact'
        },
        button: {
          Accept: 'Accept',
          Accepted: 'Accepted'
        },
        placeholder: {
          onlineServiceImpact: 'Please specify the impact',
          applicationImpact: 'Please specify the impact',
          systemImpact: 'Please specify the impact'
        },
        validateMessage: {
          onlineServiceImpactSelect: 'Please select Online Service Impact',
          applicationImpactSelect: 'Please select Backend Application/Function Impact',
          systemImpactSelect: 'Please select System/Platform Impact',
          impactRangeDate: {
            blank: 'Please select time',
            invalid: 'Online service impact time should later than expected change time'
          },
          onlineServiceImpact: 'Please input online service impact',
          applicationImpact: 'Please input application impact',
          systemImpact: 'Please input system impact'
        }
      },
      riskAssessmentAndSecurityInfo: {
        title: 'Risk Assessment And Security Information',
        label: {
          secAdminRef: 'For SecAdmin Reference',
          pidcSubmissionLink: 'PIDC Submission Link for Reference',
          riskLevel: 'Risk Level (For Low, no Risk Assessment is needed and Online Service Impact can not be "Yes")',
          riskLevelCalculation: 'Risk Level calculation method',
          riskAssessment: 'Please supplement risk calculation [(IM)X(LK)X(CM)] rating to team leader to review your change if ' +
            'it is for a "High" or "Medium" risk change',
          raReviewResult: 'RA Review Result',
          raProgress: 'RA Progress',
          raRemark: 'RA Comment'
        },
        validateMessage: {
          riskLevel: 'Please select Risk Level!'
        }
      },
      basicInfo: {
        title: 'Basic Information',
        label: {
          carrier: 'Carrier',
          category: 'Change Category',
          recipients: 'Recipients',
          service: 'Service',
          locations: 'Location',
          zones: 'Zone',
          changeType: 'Type of Change',
          changeClassification: 'Classification of Change',
          subjectOfChange: 'Subject Of Change',
          involvedPlatform: 'Production Platform Involve',
          expectedTime: 'Expected Change Time(HKT)',
          ttNumber: 'To Fix for TT/ESR No',
          ttLevel: 'TT/PR level'
        },
        placeholder: {
          category: 'Please select Carrier first then choose category',
          recipients: 'Multiple email addresses separated by ";" or ","',
          service: 'Select service',
          locations: 'Select location',
          zones: 'Select zone',
          changeType: 'Select type of change',
          changeClassification: 'Select classification of change',
          subjectOfChange: 'Please input the subject of this change request',
          involvedPlatform: 'Please input the involved production platform of this change request',
          expectedTime: 'Select expected change time',
          ttNumber: 'Separate multiple values with semicolon,Such as TT-00000000;PR-00000000;ESR-00000000...',
          ttLevel: 'Select TT level'
        },
        validateMessage: {
          carrier: 'Please select at least one carrier',
          category: 'Please select category',
          service: 'Please select service',
          locations: 'Please select at least one location',
          zones: 'Please select at least one zone',
          changeType: {
            blank: 'Please select type of change',
            invalid: 'Not allow to select admin task for this change category'
          },
          changeClassification: 'Please select at least one Classification of Change',
          subjectOfChange: {
            blank: 'Please input Subject Of Change',
            whitespace: 'You must input a non-blank value',
            length510Message: 'You must specify a value length less than 510'
          },
          involvedPlatform: {
            blank: 'Please input Production Platform Involve',
            whitespace: 'You must input a non-blank value',
            length125Message: 'You must specify a value length less than 125'
          },
          expectedTime: {
            blank: 'Please input Expected Change Time',
            invalid: 'Schedule for planned change request should be submitted at lease 2 days before the schedule'
          },
          ttNumber: {
            invalid: 'TT/ESR number is invalid. such as TT-00000000;PR-00000000;ESR-00000000;...',
            inactive: '{ttNumber} do not exist in HKCTR Portal',
            blank: 'TT/ESR number should not be blank'
          },
          ttLevel: 'The level of TT/ESR number should not be blank'
        },
        confirmContent: {
          expectedTime: 'Are you sure to select expected change time before current time? If you choose \'OK\', that means this change request has already been done.'
        },
        endorser: {
          label: {
            endorser: 'Endorser',
            team: 'Team'
          },
          placeholder: {
            team: 'Select Team',
            endorser: 'Select Endorser'
          },
          validateMessage: {
            select: 'You must select a non-blank value!',
            teams: 'Please select Teams',
            endorsers: 'Please select at least one Endorsers!',
            needOtherEndorser: 'Need the endorser of other carrier to endorsed.',
            needOwnCarrier: 'At least one endorser with own carrier is required.',
            category: 'Please select category first.',
            add: 'Add team and endorser'
          }
        }
      },
      nonProdTestingInfo: {
        title: 'Testing and Pre-Prod Information',
        label: {
          testingResult: 'QA/UAT Testing Result',
          ppTrialRunResult: 'Pre-Prod Trial Run Result',
          testEnv: 'Testing Environment'
        },
        validateMessage: {
          testEnv: 'Please input Test Environment',
          result: 'Please select Testing Result'
        },
        button: {
          Accept: 'Accept',
          Accepted: 'Accepted'
        }
      },
      referenceInfo: {
        title: 'Reference Information',
        label: {
          impactTTNumber: 'TT/PR(s) that is caused by this change',
          itsmProblem: 'To Fix for ITSM Problem',
          itsmIncident: 'To Fix for ITSM Incident',
          referenceNumber: 'Reference Number',
          relatedPCRS: 'Related PCRS(s)',
          relatedITSM: 'Related ITSM Change',
          changeTeamReference: 'Change Control Team\'s Reference'
        },
        validateMessage: {
          itsmProblem: 'Please input a valid Related ITSM Problem. such as PByyyyddmm0000;PByyyyddmm0001;...',
          itsmIncident: 'Please input a valid Related ITSM Incident. such as INCyyyyddmm0000;INCyyyyddmm0001;...',
          relatedITSM: 'Please input a valid Related ITSM Change. such as Cyyyyddmm0000;Cyyyyddmm0001;...',
          relatedPCRS: 'Please input a valid Related PCRS. such as PCRS-0000001;PCRS-0000002;...'
        },
        placeholder: {
          impactTTNumber: 'Separate multiple values with semicolon, Such as TT-00000000;PR-00000000;ESR-00000000...',
          itsmProblem: 'Seperate multiple values with semicolon,Such as PByyyyddmm0000;PByyyyddmm0001...',
          itsmIncident: 'Seperate multiple values with semicolon,Such as INCyyyyddmm0000;INCyyyyddmm0001...',
          relatedPCRS: 'Seperate multiple values with semicolon,Such as PCRS-0000001;PCRS-0000002;...',
          relatedITSM: 'Seperate multiple values with semicolon,Such as Cyyyyddmm0000;Cyyyyddmm0001...'
        },
        button: {
          save: 'Save'
        }
      }
    },
    attachment: {
      title: 'Attachment',
      label: {
        addAttachment: 'Add Attachment'
      }
    },
    conversation: {
      title: 'Conversation History',
      columns: {
        postFrom: 'From',
        postTo: 'To',
        description: 'Message',
        createdTime: 'Created Time',
        attachment: 'Attachment'
      }
    },
    task: {
      title: 'Task',
      procedure: {
        columns: {
          procedureDesc: 'Procedure',
          working: 'Handle By',
          verify: 'Handle By',
          recovery: 'Handle By',
          taskStatus: 'Task Status',
          taskRemark: 'Task Remark'
        },
        button: {
          setTaskStatus: 'Set All Task Completed',
          working: 'Add Working Procedure',
          recovery: 'Add Recovery Procedure',
          verify: 'Add verification Procedure'
        },
        validateMessage: {
          procedureDesc: 'Please input Procedure',
          blankHandledBy: 'Please input Handle By',
          taskStatus: 'Please select Task Status'
        }
      },
      workingProcedure: {
        title: 'Working Procedure',
        label: {
          workingDuration: 'Duration',
          changeCoordinator: 'Change Co-ordinator',
          contactNumber: 'Contact Number'
        },
        placeholder: {
          workingDuration: 'Please select duration',
          changeCoordinator: 'Please input change coordinator',
          contactNumber: 'Please input contact number'
        },
        validateMessage: {
          workingDuration: 'Please input Working Procedure duration',
          changeCoordinator: 'Please input Change Co-ordinator'
        }
      },
      verificationProcedure: {
        title: 'Verification Procedure',
        verification: {
          title: {
            popConfirmRecutoverModel: 'Please advise if need to submit a new PCRS to re-cutover?',
            popConfirmFallbackModel: 'Please confirm if need to rollback?'
          },
          label: {
            verificationDuration: 'Duration',
            firstVerifier: 'First Verifier',
            secondVerifier: 'Second Verifier',
            recoveryVerifier: 'Recovery Verifier',
            ContactNo: 'Contact Number',
            verificationDeadline: 'Verification Deadline(HKT)',
            firstVerificationResult: 'First Verification Result',
            secondVerificationResult: 'Second Verification Result',
            recoveryVerificationResult: 'Recovery Verification Result',
            verificationReason: 'Issue Found',
            verificationImpact: 'Any Production Service/Function Being Impacted',
            reCutoverNumber: 'Re-cutover PCRS'
          },
          validateMessage: {
            verifier: 'Please input Verifier',
            duplicateVerifier: "Please don't input duplicate Verifiers",
            ContactNo: 'Please input Contact Number',
            verificationDeadline: {
              blank: 'Please Select a verification Deadline(HKT)',
              invalid: 'Verification Deadline(HKT) should later than expected time'
            },
            verificationDuration: 'Please select Verification Procedure duration ',
            reCutoverNumber: {
              blank: 'Please input Re-cutover PCRS',
              invalid: 'Please input valid PCRS number, like PCRS-0000001...'
            },
            VerificationResult: 'Please select Verification Result.'
          },
          placeholder: {
            verificationDuration: 'Please select Duration',
            verifier: 'Personal domain ID',
            ContactNo: 'Please input Contact Number'
          }
        }
      },
      recoveryProcedure: {
        title: 'Recovery Procedure',
        label: {
          recoveryDuration: 'Duration',
          isRequireFallback: 'Require Fallback?',
          yes: 'Yes',
          no: 'No',
          reasonForFallbackAction: 'Reason For No Fallback Action'
        },
        validateMessage: {
          isRequireFallback: 'Please select is require fallback',
          recoveryDuration: 'Please select Recovery Procedure duration',
          reasonForFallbackAction: 'Please input Reason For No Fallback Action'
        },
        placeholder: {
          recoveryDuration: 'Please select duration'
        }
      }
    },
    message:{
      create: 'Create Successfully!',
      update: 'Update Successfully!'
    },
    button:{
      update: 'Update',
      cancel: 'Cancel',
      endorse: 'Endorse',
      review: 'Review',
      waitingForOtherAction: 'Waiting For Other Action',
      approve: 'Approve',
      reject: 'Reject',
      withdraw: 'Withdraw',
      postmortem: 'Postmortem',
      pending: 'Pending',
      reply: 'Reply',
      submit: 'Submit',
      draft: 'Save to Draft'
    }
  },
  reply: {
    title: 'Reply Information',
    content: {
      postTo: 'To',
      postCC: 'CC',
      description: 'Response Message',
      selectAll: 'Select All'
    },
    validateMessage: {
      postTo: 'Please select at least one',
      description: 'You must input a non-blank value'
    },
    button: {
      cancel: 'Cancel',
      save: 'Save'
    },
    actionMessage: {
      success: 'Reply information successful!',
      fail: 'Reply information failed!'
    }
  },
  postmortem: {
    title: 'Postmortem Information',
    content: {
      formNumber: 'PCRS No.',
      expectedTime: 'expectedTime',
      categoryName: 'Application'
    },
    label: {
      attendee: 'Invitee/Attendee List',
      problem: 'Description of Problem/Issue',
      rootCause: 'Cause of Problem/Issue',
      businessImpact: 'Production Function/Business is Being Impacted',
      correctAction: 'Corrective Action',
      preventiveAction: 'Preventive Action',
      conclusion: 'Conclusion of the Failed/Partial Success'
    },
    button: {
      cancel: 'Cancel',
      save: 'Save'
    },
    actionMessage: {
      success: 'Postmortem information successful!',
      fail: 'Postmortem information failed!'
    }
  },
  home: {
    button: {
      newSubmission: 'New Submission'
    },
    copyAsNew: {
      popUpTitle: 'Copy As New',
      label: 'Existing PCRS No.',
      okText: 'OK',
      cancelText: 'Cancel',
      validateMessage: {
        pcrsNumberIsValid: 'Please input a valid PCRS number, such as PCRS-0000001'
      }
    },
    tab: {
      toDoList: 'To-Do List',
      mySubmission: 'My Submission',
      doneList: 'Done List'
    },
    gridColumns: {
      pcrsNo: 'PCRS No.',
      carrier: 'Carrier',
      category: 'Category',
      typeOfChange: 'Change Type',
      subject: 'Subject',
      expectedChangeTime: 'Schedule Time',
      requester: 'Requester',
      endorsers: 'Endorsers',
      qaUatTesting: 'QA/UAT Testing',
      ppTrialRun: 'PP Trial Run',
      status: 'Status',
      progress: 'Progress',
      workingExecutors: 'Executors',
      verifiers: '1st & 2nd verifiers',
      verifyExecutors: 'Verify Executors',
      verificationDeadline: 'Ver Deadline'
    },
    todo: {
      pendingForEndorsement: 'Pending for My Endorsement',
      pendingForVerification: 'Pending for My Verification',
      pendingForExecution: 'Pending for My Execution'
    },
    mySubmission: {
      inDraft: 'In Draft',
      inProgress: 'In Progress',
      inCompleted: 'Completed'
    },
    doneList: {
      myEndorsement: 'My Endorsement',
      myVerification: 'My Verification',
      myExecution: 'My Execution'
    },
    menu: {
      category: 'Category List',
      home: 'Home',
      team: 'Team/Endorser List',
      dictionary: 'Dictionary',
      maintenance: 'Maintenance',
      search: 'Search',
      'email-template': 'Email Template',
      HKCTRView: 'HKCTR View',
      changeControlView: 'Change Control View',
      'request-status': 'Request Status'
    }
  },
  hkctrView: {
    todo: {
      pendingForReview: 'Pending for Review',
      pendingForVerification: 'Pending for Verification',
      pendingForExecution: 'Pending for Execution'
    },
    gridColumns: {
      pcrsNo: 'PCRS No.',
      carrier: 'Carrier',
      category: 'Category',
      subject: 'Subject',
      requester: 'Requester',
      typeOfChange: 'Change Type',
      expectedChangeTime: 'Schedule Time',
      endorsedBy: 'Endorsed By',
      raProgress: 'RA Progress',
      status: 'Status',
      qaUatTesting: 'QA/UAT Testing',
      ppTrialRun: 'PP Trial Run',
      verificationDeadline: 'Ver Deadline'
    },
    buttons: {
      hkctrToDo: {
        title: 'HKCTR To-do',
        buttonDesc: {
          executionAdnVerificationAndReview: 'HKCTR To-do'
        }
      },
      pcrsSchedule: {
        title: 'PCRS Schedule',
        buttonDesc: {
          theNextTwoHours: 'The Next 2 Hours',
          iris4WeekendChange: 'IRIS-4 Weekend Change'
        }
      },
      pcrsTaskStatus: {
        title: 'PCRS Task Status',
        buttonDesc: {
          executionDelay: 'Execution Delay',
          workingInProgress: 'Working in Progress',
          runOutOfDuration: 'Run Out of Duration',
          verificationDelay: 'Verification Delay'
        }
      }
    }
  },
  timeViewer: {
    button: {
      moreCites: 'More Cities',
      close: 'Close'
    }
  },
  search: {
    label: {
      carrier: 'Carrier',
      changeType: 'Change Type',
      requester: 'Requester',
      subject: 'Subject',
      changeMainCategory: 'Change Main Category',
      changeSubCategory: 'Change Sub Category',
      expectedDateChange: 'Schedule Time',
      riskLevel: 'Risk Level',
      raReviewResult: 'RA Review Result',
      status: 'Status',
      team: 'Team',
      isRecutover: 'Is Re-cutover'
    },
    placeholder: {
      carrier: 'Please select',
      changeType: 'Please select',
      requester: 'Please input',
      subject: 'Please input',
      changeMainCategory: 'Please select',
      changeSubCategory: 'Please select',
      expectedDateChangeStart: 'Start Date',
      expectedDateChangeEnd: 'End Date',
      riskLevel: 'Please select',
      raReviewResult: 'Please select',
      status: 'Please select',
      team: 'Please select'
    },
    value: {
      selectOne: 'Please Select'
    },
    button: {
      search: 'Search',
      reset: 'Reset',
      export: 'Export as Excel',
      foldSearch: 'Fold Search',
      moreSearch: 'More Search'
    },
    table: {
      label: {
        searchResult: 'Search Result',
        total: 'Total',
        records: 'Records'
      },
      gridColumns: {
        pcrsNo: 'PCRS No.',
        carrier: 'Carrier',
        category: 'Category',
        typeOfChange: 'Change Type',
        subject: 'Subject',
        expectedChangeTime: 'Schedule Time',
        requester: 'Requester',
        involvedPlatform: 'Platform Involve',
        team: 'Team',
        endorsedBy: 'Endorsed By',
        status: 'Status',
        verifiers: '1st & 2nd verifiers',
        verificationDeadline: 'Ver Deadline'
      }
    },
  },
  refreshModel: {
    title: 'Confirm',
    submit: 'You have submitted this PCRS successfully',
    endorse: 'You have endorsed this PCRS successfully',
    review: 'You have reviewed this PCRS successfully',
    approve: 'You have approved this PCRS successfully',
    waitForOtherAction: 'You have updated successfully',
    reject: 'You have rejected this PCRS successfully',
    withDraw: 'You have withdrawn this PCRS successfully',
    draft: 'You have drafted this PCRS successfully',
    pending: 'You have updated this PCRS successfully',
    verify: 'You have updated this PCRS successfully',
    execute: 'You have updated this PCRS successfully',
    update: 'You have updated this PCRS successfully',
    verifyExecute: 'You have updated this PCRS successfully',
    recoveryVerify: 'You have updated this PCRS successfully',
    recoveryExecute: 'You have updated this PCRS successfully',
    recutover: 'You have updated this PCRS successfully',
    ok: 'OK'
  },
  tipsAlertModel: {
    title: 'Confirm',
    reject: 'Are you sure to reject this PCRS ?',
    withdraw: 'Are you sure to withdraw this PCRS ?',
    cancel: 'Are you sure to cancel the update?',
    ok: 'Yes',
    no: 'No'
  },
  validateAlertModel: {
    title: 'Tips',
    content: "Note that below error validation message(s):",
    ok: 'I Know'
  },
  errorMessage: {
    ERR_CODE_OPTIMISTIC_LOCKING_FAILURE: 'Others have updated the form, please re-load page.'
  }
};
